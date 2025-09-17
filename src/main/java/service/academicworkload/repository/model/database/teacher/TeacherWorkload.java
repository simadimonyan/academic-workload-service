package service.academicworkload.repository.model.database.teacher;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.workspace.Workspace;

/**
 * Таблица рабочего графика преподавателя
 */
@Data
@Entity
@Table(name = "teacher_workload_table")
public class TeacherWorkload {

    @Id
    @Column(name = "workload_id")
    @GeneratedValue
    private Long id;

    // связь с рабочим пространством
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // связь с преподавателем (FK)
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // день недели
    @Column(name = "day_week")
    private String dayWeek;

    // начало пары
    @Column(name = "started_at")
    private String startedAt;

    // конец пары
    @Column(name = "finished_at")
    private String finishedAt;

}
