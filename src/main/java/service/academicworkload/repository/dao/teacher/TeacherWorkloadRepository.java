package service.academicworkload.repository.dao.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.academicworkload.repository.model.database.teacher.TeacherWorkload;

@Repository
public interface TeacherWorkloadRepository extends JpaRepository<TeacherWorkload, Long> {}
