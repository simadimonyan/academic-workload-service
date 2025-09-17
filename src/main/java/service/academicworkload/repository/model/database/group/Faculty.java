package service.academicworkload.repository.model.database.group;

import io.micrometer.core.annotation.Counted;
import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.auditory.Block;
import service.academicworkload.repository.model.database.workspace.Workspace;

import java.util.List;

/**
 * Таблица факультетов (отделений)
 */
@Data
@Entity
@Table(name = "faculty_table")
public class Faculty {

    @Id
    @Column(name = "faculty_id")
    @GeneratedValue
    private Long id;

    // связь с рабочим пространством
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // связка с закрепленным корпусом (FK)
    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Group> groups;

    // название факультета (отделение)
    @Column(name = "name")
    private String name;

}
