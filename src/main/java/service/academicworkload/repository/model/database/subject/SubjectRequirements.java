package service.academicworkload.repository.model.database.subject;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.workspace.Workspace;

/**
 * Таблица требований к оснащению
 * для работы с дисциплиной
 */
@Data
@Entity
@Table(name = "subject_requirements_table")
public class SubjectRequirements {

    @Id
    @Column(name = "requirement_id")
    @GeneratedValue
    private Long id;

    // связь с рабочим пространством
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // связь с предметом (FK)
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // название требования
    @Column(name = "name")
    private String name;

    // количество (при необходимости)
    @Column(name = "count")
    private String count;

}
