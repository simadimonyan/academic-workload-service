package service.academicworkload.repository.model.database.subject;

import jakarta.persistence.*;
import lombok.Data;
import service.academicworkload.repository.model.database.workload.AcademicWorkload;

import java.util.List;

/**
 * Таблица предметов используется
 * для хранения дисциплин по нагрузке
 */
@Data
@Entity
@Table(name = "subject_table")
public class Subject {

    @Id
    @Column(name = "subject_id")
    @GeneratedValue
    private Long id;

    // учебная нагрузка на дисциплину по группам
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<AcademicWorkload> workload;

    // требования к предмету
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<SubjectRequirements> requirements;

    @Column(name = "name")
    private String name;

    @Column(name = "subject_type")
    private String subjectType;

}
