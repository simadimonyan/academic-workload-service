package service.academicworkload.repository.model.database.auditory;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.group.Faculty;
import service.academicworkload.repository.model.database.workspace.Workspace;

import java.util.List;

/**
 * Таблица корпусов учреждения
 */
@Data
@Entity
@Table(name = "block_table")
public class Block {

    @Id
    @Column(name = "block_id")
    @GeneratedValue
    private Long id;

    // связь с рабочим пространством
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // аудитории корпуса
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<Auditory> auditories;

    // закрепленные за корпусом отделения
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<Faculty> faculties;

    // название корпуса
    @Column(name = "name")
    private String name;

    // адрес корпуса
    @Column(name = "address")
    private String address;

    // примечание
    @Column(name = "note")
    private String note;

}
