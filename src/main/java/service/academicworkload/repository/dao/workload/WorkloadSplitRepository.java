package service.academicworkload.repository.dao.workload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.academicworkload.repository.model.database.workload.WorkloadSplit;

import java.util.Optional;

@Repository
public interface WorkloadSplitRepository extends JpaRepository<WorkloadSplit, Long> {

    Optional<Integer> countByWeekCountAndSubjectCount(Integer weekCount, Integer subjectCount);

}
