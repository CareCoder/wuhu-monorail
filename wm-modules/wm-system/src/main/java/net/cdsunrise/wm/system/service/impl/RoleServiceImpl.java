package net.cdsunrise.wm.system.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.system.entity.Resource;
import net.cdsunrise.wm.system.entity.Role;
import net.cdsunrise.wm.system.entity.User;
import net.cdsunrise.wm.system.repository.RoleRepository;
import net.cdsunrise.wm.system.repository.UserRepository;
import net.cdsunrise.wm.system.service.RoleService;
import net.cdsunrise.wm.system.vo.ResourcesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/25
 * Describe :
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Override
    public void save(Role role) {
        role.setRoleAuthorId(userRepository.findOne(UserUtils.getUserId()).getRole().getId());
        roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        Role role = roleRepository.findOne(id);
        //设置user表数据中关联的Role属性为null
        List<User> userList = userRepository.findByRole(role);
        for (User user : userList) {
            user.setRole(null);
        }
        userRepository.save(userList);
        //删除该角色在wm_role_right表的对应关联数据
        roleRepository.deleteRoleRight(id);
        roleRepository.delete(id);
    }

    @Override
    public List<Role> findAll() {
        Long authorId = userRepository.findOne(UserUtils.getUserId()).getRole().getId();
        QueryHelper helper = new QueryHelper(Role.class)
                .addCondition(" roleAuthorId = ?", authorId)
                .useNativeSql(false);
        List<Role> list = commonDAO.findList(helper);
        return list;

    }

    @Override
    public Role findOne(Long id) {
        return roleRepository.findOne(id);
    }

    /**
     * 更新角色权限菜单
     *
     * @param resourcesIds
     * @param id
     */
    @Override
    public void updateRoleRight(List<Long> resourcesIds, Long id) {
        List<Resource> resources = new ArrayList<>();
        resourcesIds.forEach(rid -> {
            Resource resource = new Resource();
            resource.setId(rid);
            resources.add(resource);
        });
        Role role = roleRepository.findOne(id);
        role.setResources(resources);
        roleRepository.save(role);
    }

    /**
     * 查看角色权限菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<ResourcesVo> viewRoleResource(Long roleId) {
        QueryHelper helper = new QueryHelper("w.resource_id as resourcesId", "wm_role_right w")
                .addCondition("role_id = ?", roleId)
                .useNativeSql(true);
        List<ResourcesVo> list = commonDAO.findList(helper, ResourcesVo.class);
        return list;
    }

}
