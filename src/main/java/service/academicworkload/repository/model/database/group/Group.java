package service.academicworkload.repository.model.database.group;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.workload.AcademicWorkload;

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
    @ManyToMany
    @JoinTable(
        name = "group_theory_period",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "theory_id")
    )
    private List<TheoryPeriod> study;

    // название группы
    @Column(name = "name")
    private String name;

    // уровень образования (СПО, Бакалавриат, Магистратура)
    @Column(name = "level")
    private String level;

    // курс группы
    @Column(name = "course")
    private Integer course;

    // форма обучения
    @Column(name = "study_form")
    private String studyForm;

    // количество студентов
    @Column(name = "capacity")
    private Integer capacity;

}
