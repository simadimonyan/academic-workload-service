package service.academicworkload.repository.dao.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.academicworkload.repository.model.database.group.TheoryPeriod;

import java.util.Optional;

@Repository
public interface TheoryPeriodRepository extends JpaRepository<TheoryPeriod, Long> {

    Optional<TheoryPeriod> findByTheoryStartAndTheoryEnd(String theoryStart, String theoryEnd);

}
