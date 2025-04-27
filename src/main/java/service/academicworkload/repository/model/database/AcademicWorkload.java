package service.academicworkload.repository.model.database;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.group.Department;
import service.academicworkload.repository.model.database.group.Group;
import service.academicworkload.repository.model.database.subject.Subject;
import service.academicworkload.repository.model.database.teacher.Teacher;

/**
 * Таблица академической нагрузки
 */
@Data
@Entity
@Table(name = "academic_workload_table")
public class AcademicWorkload {

    @Id
    @Column(name = "workload_id")
    @GeneratedValue
    private Long id;

    // связь с дисциплиной (FK)
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // связь с преподавателем (FK)
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // связь с группой (FK)
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    // связь с кафедрой (FK)
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // тип работ (Пр, Лек, Лаб)
    @Column(name = "type")
    private String type;

    // семестр
    @Column(name = "semester")
    private Integer semester;

    // часы нагрузки на дисциплину
    @Column(name = "hours")
    private Integer hours;

    // количество недель нагрузки на дисциплину
    @Column(name = "weeks")
    private Integer weeks;

}
