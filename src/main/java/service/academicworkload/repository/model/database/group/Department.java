package service.academicworkload.repository.model.database.group;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.workload.AcademicWorkload;
import service.academicworkload.repository.model.database.workspace.Workspace;

import java.util.List;

/**
 * Таблица кафедр
 */
@Data
@Entity
@Table(name = "department_table")
public class Department {

    @Id
    @Column(name = "department_id")
    @GeneratedValue
    private Long id;

    // связь с рабочим пространством
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // академическая нагрузка по кафедре
    @OneToMany(mappedBy = "department")
    private List<AcademicWorkload> workload;

    // название кафедры
    @Column(name = "name")
    private String name;

}
