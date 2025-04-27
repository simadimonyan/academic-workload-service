package service.academicworkload.repository.model.database.group;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.AcademicWorkload;

import java.util.List;

/**
 * Таблица обучающихся групп
 */
@Data
@Entity
@Table(name = "group_table")
public class Group {

    @Id
    @Column(name = "group_id")
    @GeneratedValue
    private Long id;

    // академическая нагрузка группы
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<AcademicWorkload> workload;

    // связь с факультетом (FK)
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    // связь с графиком обучения (FK)
    @ManyToOne
    @JoinColumn(name = "theory_id")
    private TheoryPeriod study;

    // название группы
    @Column(name = "name")
    private String name;

    // курс группы
    @Column(name = "course")
    private Integer course;

    // количество студентов
    @Column(name = "capacity")
    private Integer capacity;

}
