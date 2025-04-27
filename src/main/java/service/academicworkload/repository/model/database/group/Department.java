package service.academicworkload.repository.model.database.group;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.AcademicWorkload;

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

    // академическая нагрузка по кафедре
    @OneToMany(mappedBy = "department")
    private List<AcademicWorkload> workload;

    // название кафедры
    @Column(name = "name")
    private String name;

}
