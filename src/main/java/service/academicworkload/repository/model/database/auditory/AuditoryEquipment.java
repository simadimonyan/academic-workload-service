package service.academicworkload.repository.model.database.auditory;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.workspace.Workspace;

/**
 * Таблица оснащения аудиторий
 */
@Data
@Entity
@Table(name = "equipment_table")
public class AuditoryEquipment {

    @Id
    @Column(name = "equipment_id")
    @GeneratedValue
    private Long id;

    // связь с рабочим пространством
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // связка с аудиторией (FK)
    @ManyToOne
    @JoinColumn(name = "auditory_id")
    private Auditory auditory;

    // название оснащения
    @Column(name = "name")
    private String name;

    // количество оснащения (при необходимости)
    @Column(name = "count")
    private Integer count;

}
