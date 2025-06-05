package service.academicworkload.repository.dao.auditory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.academicworkload.repository.model.database.auditory.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {}
