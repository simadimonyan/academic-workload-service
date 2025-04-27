package service.academicworkload.repository.model.database.auditory;

import jakarta.persistence.*;
import lombok.Data;

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
