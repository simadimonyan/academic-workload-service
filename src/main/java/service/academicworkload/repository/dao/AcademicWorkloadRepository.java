package service.academicworkload.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.academicworkload.repository.model.database.workload.AcademicWorkload;

@Repository
public interface AcademicWorkloadRepository extends JpaRepository<AcademicWorkload, Long> {

}
