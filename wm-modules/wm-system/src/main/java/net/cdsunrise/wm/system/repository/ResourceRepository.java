package net.cdsunrise.wm.system.repository;

import net.cdsunrise.wm.system.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/24
 * Describe :
 */
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByType(Integer type);

    @Query(value = "select * from wm_resource  where id in(SELECT resource_id from wm_role_right where role_id = (select role_id from wm_user where id = ?1)) AND type =3 AND path = ?2",nativeQuery = true)
    Resource judgeUserType(Long id, String code);
}
