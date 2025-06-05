package service.academicworkload.repository.model.database.workload;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Таблица, хранящая распределение
 * нагрузки по числу пар в неделю
 */
@Data
@Entity
@Table(name = "workload_split_table")
public class WorkloadSplit {

    @Id
    @GeneratedValue
    private Long id;

    // связь с нагрузкой
    @ManyToOne
    @JoinColumn(name = "workload_id")
    private AcademicWorkload workload;

    // номер недели периода обучения
    @Column(name = "week_count")
    private Integer weekCount;

    // количество пар дисциплины по номеру недели
    @Column(name = "subject_count")
    private Integer subjectCount;

}
