package service.academicworkload.service.process;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.academicworkload.repository.dao.AcademicWorkloadRepository;
import service.academicworkload.repository.dao.group.DepartmentRepository;
import service.academicworkload.repository.dao.group.FacultyRepository;
import service.academicworkload.repository.dao.group.GroupRepository;
import service.academicworkload.repository.dao.group.TheoryPeriodRepository;
import service.academicworkload.repository.dao.subject.SubjectRepository;
import service.academicworkload.repository.dao.teacher.TeacherRepository;
import service.academicworkload.repository.model.database.workload.AcademicWorkload;
import service.academicworkload.repository.model.database.group.Department;
import service.academicworkload.repository.model.database.group.Faculty;
import service.academicworkload.repository.model.database.group.Group;
import service.academicworkload.repository.model.database.group.TheoryPeriod;
import service.academicworkload.repository.model.database.subject.Subject;
import service.academicworkload.repository.model.database.teacher.Teacher;
import service.academicworkload.service.csv.model.CsvWorkload;
import service.academicworkload.service.csv.model.CsvDepartment;
import service.academicworkload.service.csv.model.CsvGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkloadFactoryService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final TheoryPeriodRepository theoryPeriodRepository;
    private final GroupRepository groupRepository;
    private final AcademicWorkloadRepository academicWorkloadRepository;

    private static final Logger logger = LoggerFactory.getLogger(WorkloadFactoryService.class);

    @Autowired
    public WorkloadFactoryService(
        SubjectRepository subjectRepository,
        TeacherRepository teacherRepository,
        DepartmentRepository departmentRepository,
        FacultyRepository facultyRepository,
        TheoryPeriodRepository theoryPeriodRepository,
        GroupRepository groupRepository,
        AcademicWorkloadRepository academicWorkloadRepository
    ) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
        this.theoryPeriodRepository = theoryPeriodRepository;
        this.groupRepository = groupRepository;
        this.academicWorkloadRepository = academicWorkloadRepository;
    }

    @Transactional
    public void process(
        ArrayList<CsvWorkload> csvWorkloads,
        ArrayList<CsvGroup> csvGroups,
        ArrayList<CsvDepartment> csvDepartments
    ) throws NoSuchFieldException, IllegalAccessException {

        int index = 0;
        for (CsvWorkload csvWorkload : csvWorkloads) {

            index++;
            logger.info("Обработка записи #{}: группа={}, предмет={}, преподаватель={}",
                    index, csvWorkload.getGroup(), csvWorkload.getSubject(), csvWorkload.getTeacher());

            // получение данных по связям
            logger.debug("Поиск группы {}", csvWorkload.getGroup());
            CsvGroup csvGroup = csvGroups.stream().filter(g -> g.getGroup()
                    .equals(csvWorkload.getGroup())).findFirst().orElseThrow();

            logger.debug("Поиск кафедры по коду {}", csvWorkload.getDepartmentId());
            CsvDepartment csvDepartment = csvDepartments.stream().filter(d -> d.getCode()
                    .equals(csvWorkload.getDepartmentId())).findFirst().orElseThrow();

            // -- период обучения --
            logger.debug("Обработка периодов обучения для группы {}", csvGroup.getGroup());
            List<TheoryPeriod> periods = new ArrayList<>();

            // 4 периода - по csv (макс)
            for (int i = 1; i <= 4; i++) {
                Field startField = CsvGroup.class.getDeclaredField("periodStart" + i);
                Field endField = CsvGroup.class.getDeclaredField("periodEnd" + i);

                startField.setAccessible(true); // модификатор доступа
                endField.setAccessible(true);

                String start = (String) startField.get(csvGroup);
                String end = (String) endField.get(csvGroup);

                if (start != null && !start.isBlank()
                        && end != null && !end.isBlank()
                        && !start.equalsIgnoreCase("30.12.1899") // нет периода
                        && !end.equalsIgnoreCase("30.12.1899")
                ) {
                    // если в базе не существует
                    TheoryPeriod theoryPeriod = theoryPeriodRepository.findByTheoryStartAndTheoryEnd(start, end).orElseGet(() -> {
                        TheoryPeriod savable = new TheoryPeriod();
                        savable.setTheoryStart(start);
                        savable.setTheoryEnd(end);
                        return theoryPeriodRepository.save(savable);
                    });
                    periods.add(theoryPeriod);
                }
            }

            // -- факультет --
            logger.debug("Поиск или создание факультета: {}", csvGroup.getFaculty());
            Faculty faculty = facultyRepository.findAllByName(csvGroup.getFaculty()).orElseGet(() -> {
                Faculty savable = new Faculty();
                savable.setName(csvGroup.getFaculty());
                return  facultyRepository.save(savable);
            });

            // -- группа --

            // получаем форму обучения
            String formStudy = switch(csvGroup.getStudyForm()) {
                case 1 -> "Очная";
                case 2 -> "Заочная";
                case 3 -> "Очно-заочная";
                default -> throw new IllegalStateException("Unexpected value: " + csvGroup.getStudyForm());
            };

            logger.debug("Поиск или создание группы: {}", csvGroup.getGroup());
            Group group = groupRepository.findAllByName(csvGroup.getGroup()).orElseGet(() -> {
                Group savable = new Group();
                savable.setFaculty(faculty); // связь
                savable.setName(csvGroup.getGroup());
                savable.setCapacity(csvGroup.getCapacity());
                savable.setCourse(csvGroup.getCourse());
                savable.setStudyForm(formStudy);

                String level = csvGroup.getGroup().contains("СПО") ? "СПО" : (csvGroup.getGroup().contains("Мг") ? "Магистратура" : "Бакалавриат");
                savable.setLevel(level);
                savable.setStudy(periods);

                return groupRepository.save(savable);
            });

            // -- кафедра --
            logger.debug("Поиск или создание кафедры: {}", csvDepartment.getName());
            Department department = departmentRepository.findAllByName(csvDepartment.getName()).orElseGet(() -> {
                Department savable = new Department();
                savable.setName(csvDepartment.getName());
                return departmentRepository.save(savable);
            });

            // -- предмет --
            /*
            if (csvWorkload.getSubjectType().contains("Лек")  // если тип работ - лекция
                    || csvWorkload.getSubjectType().contains("Пр") // если тип работ - практика
                    || csvWorkload.getSubjectType().contains("Лаб") // если тип работ - лабораторная
            ) {

            }*/
            logger.debug("Поиск или создание предмета: {} ({})", csvWorkload.getSubject(), csvWorkload.getSubjectType());
            Subject subject = subjectRepository.findAllByNameAndSubjectType(csvWorkload.getSubject(), csvWorkload.getSubjectType()).orElseGet(() -> {
                Subject savable = new Subject();
                savable.setName(csvWorkload.getSubject());
                savable.setSubjectType(csvWorkload.getSubjectType());
                return subjectRepository.save(savable);
            });

            // -- преподаватель --
            Teacher teacher = null;
            if (!csvWorkload.getTeacher().contains("И?.")) {
                logger.debug("Поиск или создание преподавателя: {}", csvWorkload.getTeacher());
            } else {
                logger.debug("Преподаватель не указан явно, пропуск создания");
            }
            if (!csvWorkload.getTeacher().contains("И?.")) { // если преподаватель закреплен по нагрузке
                 teacher = teacherRepository.findAllByLabel(csvWorkload.getTeacher()).orElseGet(() -> {
                    Teacher savable = new Teacher();
                    savable.setLabel(csvWorkload.getTeacher());
                    savable.setStatus(csvWorkload.getTeacherStatus());
                    return teacherRepository.save(savable);
                });
            }

            // -- нагрузка --
            logger.debug("Создание академической нагрузки");
            AcademicWorkload academicWorkload = new AcademicWorkload();
            academicWorkload.setDepartment(department);
            academicWorkload.setGroup(group);
            academicWorkload.setHours(csvWorkload.getHours());
            academicWorkload.setSubject(subject);
            academicWorkload.setSemester(csvWorkload.getSemester());
            academicWorkload.setWeeks(csvWorkload.getWeeks());
            if (teacher != null) academicWorkload.setTeacher(teacher);

            // -- остаточные связи --
            // группы по теоретическому обучению
            for (TheoryPeriod period : periods) {
                if (period.getGroups() != null) {
                    period.getGroups().add(group);
                }
                else {
                    ArrayList<Group> list = new ArrayList<>();
                    list.add(group);
                    period.setGroups(list);
                }
            }

            // -- сохранение в базу --
            logger.info("Сохранение сущностей в БД для записи #{}", index);
            theoryPeriodRepository.saveAll(periods);
            facultyRepository.save(faculty);
            groupRepository.save(group);
            departmentRepository.save(department);
            subjectRepository.save(subject);
            if (teacher != null) teacherRepository.save(teacher);
            academicWorkloadRepository.save(academicWorkload);

        }

        logger.info("Обработка завершена. Всего обработано записей: {}", csvWorkloads.size());
    }
}
