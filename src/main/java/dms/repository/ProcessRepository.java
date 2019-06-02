package dms.repository;

import dms.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

    @Query("SELECT p FROM Process p WHERE p.parent.id = :parentId")
    public List<Process> findByParent(@Param("parentId") Long parentId);

    @Query("SELECT p FROM Process p WHERE p.primitive = :primitive")
    public List<Process> findByPrimitive(@Param("primitive") boolean primitive);

}
