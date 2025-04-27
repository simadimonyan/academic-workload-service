package service.academicworkload.repository.model.database.teacher;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.AcademicWorkload;
import service.academicworkload.repository.model.database.auditory.Auditory;

import java.util.List;

/**
 * Таблица преподавателей
 */
@Data
@Entity
@Table(name = "teacher_table")
public class Teacher {

    @Id
    @Column(name = "teacher_id")
    @GeneratedValue
    private Long id;

    // академическая нагрузка преподавателя по дисциплинам
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<AcademicWorkload> academicWorkload;

    // график работы преподавателя (по парам и дням)
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<TeacherWorkload> scheduleWorkload;

    // список закрепленных аудиторий
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Auditory> auditories;

    // имя преподавателя
    @Column(name = "name")
    private String name;

    // фамилия преподавателя
    @Column(name = "last_name")
    private String lastName;

    // отчество преподавателя (при наличии)
    @Column(name = "patronymic")
    private String patronymic;

    // статус преподавателя (как сотрудника)
    @Column(name = "status")
    private String status;

}
