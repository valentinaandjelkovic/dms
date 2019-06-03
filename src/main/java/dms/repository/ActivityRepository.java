package dms.repository;

import dms.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a WHERE a.process.id = :processId")
    public List<Activity> findByProcess(@Param("processId") Long processId);

    @Query("SELECT a FROM Activity a WHERE a.process.company.id = :companyId")
    List<Activity> findByCompany(@Param("companyId") Long companyId);
}
