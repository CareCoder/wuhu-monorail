package net.cdsunrise.wm.system.service;

import net.cdsunrise.wm.system.entity.Role;
import net.cdsunrise.wm.system.vo.ResourcesVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/25
 * Describe :
 */
public interface RoleService {

    void save(Role role);

    void delete(Long id);

    List<Role> findAll();

    Role findOne(Long id);

    /**
     * 更新角色权限
     * @param resourcesIds
     * @param id
     */
    void updateRoleRight(List<Long> resourcesIds, Long id);

    /**
     * 查看角色权限
     * @param roleId
     * @return
     */
    List<ResourcesVo> viewRoleResource(Long roleId);

}
