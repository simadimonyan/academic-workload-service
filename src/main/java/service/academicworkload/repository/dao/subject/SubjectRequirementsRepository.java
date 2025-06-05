package service.academicworkload.repository.dao.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.academicworkload.repository.model.database.subject.SubjectRequirements;

@Repository
public interface SubjectRequirementsRepository extends JpaRepository<SubjectRequirements, Long> {}
