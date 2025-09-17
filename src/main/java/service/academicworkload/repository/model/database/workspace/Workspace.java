package service.academicworkload.repository.model.database.workspace;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица рабочего пространства
 */
@Data
@Entity
@Table(name = "workspace_table")
public class Workspace {

    @Id
    @Column(name = "workspace_id")
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
