package net.cdsunrise.wm.system.repository;

import net.cdsunrise.wm.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author : WangRui
 * Date : 2018/4/25
 * Describe :
 */
public interface RoleRepository extends JpaRepository<Role,Long>,JpaSpecificationExecutor<Role> {

    @Modifying
    @Transactional
    @Query(value = "delete from wm_role_right where role_id = :roleId",nativeQuery = true)
    int deleteRoleRight(@Param("roleId")Long roleId);
}
