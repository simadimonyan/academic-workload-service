package service.academicworkload.repository.model.database.auditory;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.teacher.Teacher;

import java.util.List;

/**
 * Таблица аудиторий учреждения
 */
@Data
@Entity
@Table(name = "auditory_table")
public class Auditory {

    @Id
    @Column(name = "auditory_id")
    @GeneratedValue
    private Long id;

    // оснащенность аудитории
    @OneToMany(mappedBy = "auditory", cascade = CascadeType.ALL)
    private List<AuditoryEquipment> equipment;

    // связка с корпусом (FK)
    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;

    // связка с закрепленным преподавателем (FK)
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // тип аудитории (лекционный, компьютерный...)
    @Column(name = "type")
    private String type;

    // этаж аудитории
    @Column(name = "floor")
    private Integer floor;

    // номер аудитории (114а...)
    @Column(name = "number")
    private String number;

    // вместимость аудитории
    @Column(name = "capacity")
    private Integer capacity;

}
