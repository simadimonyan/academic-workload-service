package service.academicworkload.repository.model.database.group;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Таблица, хранящая период
 * теоретического обучения по группам
 */
@Data
@Entity
@Table(name = "theory_period_table")
public class TheoryPeriod {

    @Id
    @Column(name = "theory_id")
    @GeneratedValue
    private Long id;

    // группы по графику
    @ManyToMany(mappedBy = "study")
    private List<Group> groups;

    // timestamp начала теоретического обучения
    @Column(name = "theory_start")
    private String theoryStart;

    // timestamp конца теоретического обучения
    @Column(name = "theory_end")
    private String theoryEnd;

}
