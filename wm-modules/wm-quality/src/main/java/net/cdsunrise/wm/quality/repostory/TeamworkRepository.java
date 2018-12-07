package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.Teamwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface TeamworkRepository extends JpaRepository<Teamwork, Long>{
    @Transactional
    @Modifying
    @Query("update Teamwork tw set tw.status = ?1 where tw.id in (?2)")
    void publishBatch(Integer status, Long[] ids);
}
