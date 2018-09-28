package net.cdsunrise.wm.system.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.system.entity.Resource;
import net.cdsunrise.wm.system.entity.User;
import net.cdsunrise.wm.system.repository.ResourceRepository;
import net.cdsunrise.wm.system.service.ResourceService;
import net.cdsunrise.wm.system.service.UserService;
import net.cdsunrise.wm.system.vo.ResourcesPathVo;
import net.cdsunrise.wm.system.vo.ResourcesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Author : WangRui
 * Date : 2018/4/24
 * Describe :
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private UserService userService;

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Override
    public List<Resource> findUserResources(Long userId) {
        User user = userService.findOriginalUser(userId);
        List<Resource> resources = new ArrayList<>();
        for (Resource resource : user.getRole().getResources()) {
            if (resource.getType() == 1) {
                resources.add(resource);
            }
        }
        return resources;
    }

    /**
     * 查询当前登录用户的菜单权限详细信息
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> lookUpUserRight() {
        return encapsulation(findUserResources(UserUtils.getUserId()));
    }

    @Override
    public List<String> lookUpUserRightPath(Long id) {
        QueryHelper helper = new QueryHelper("w.path as path", "wm_resource w")
                .addCondition("w.id in (select resource_id from wm_role_right where role_id = " + id + ")")
                .useNativeSql(true);
        List<ResourcesPathVo> list = commonDAO.findList(helper, ResourcesPathVo.class);
        List<String> pathStringList = new ArrayList<>();
        for (ResourcesPathVo resourcesPathVo : list) {
            pathStringList.add(resourcesPathVo.getPath());
        }
        return pathStringList;
    }

    /**
     * 查询所有菜单资源
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> allMenuResources() {
        List<Resource> resourceList = resourceRepository.findByType(1);//查询所有菜单
        return encapsulation(resourceList);
    }

    /**
     * 查看某个用户的菜单权限
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> userRoleRight(Long id) {
        return encapsulation(findUserResources(id));
    }

    /**
     * 获取当前用户类型(制梁计划列表数据控制)
     *
     * @return
     */
    @Override
    public Resource fetchUserType(String code) {
        Resource resource = resourceRepository.judgeUserType(UserUtils.getUserId(), code);
        return resource;
    }

    /**
     * 封装资源格式,菜单排序
     */
    public List<Map<String, Object>> encapsulation(List<Resource> sourceList) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Resource> parentMenuResourceList = new ArrayList<>();
        int[] parentMenuOrderArray = new int[15];
        int index = 0;
        //遍历得到父级菜单顺序List
        for (Resource resource : sourceList) {
            if (resource.getParentId() == 0) {
                parentMenuResourceList.add(resource);
                parentMenuOrderArray[index] = resource.getOrder();
                index++;
            }
        }
        //父级菜单顺序数组排序
        int[] parentIdSortArray = menuIdSort(parentMenuOrderArray);
        //按父菜单order数组的从小到大顺序遍历父菜单list
        for (int i = 0; i < parentIdSortArray.length; i++) {
            if (parentIdSortArray[i] == 0) {
                continue;
            }
            for (Resource resource : parentMenuResourceList) {
                if (resource.getOrder() == parentIdSortArray[i]) {
                    Long parentId = resource.getId();
                    Map<String, Object> stringObjectMap = new HashMap<>(4);
                    stringObjectMap.put("id", parentId);
                    stringObjectMap.put("name", resource.getName());
                    stringObjectMap.put("icon", resource.getIcon());
                    stringObjectMap.put("path", resource.getPath());
                    List<Resource> childMenuResourceSortList = new ArrayList<>();
                    List<Resource> childMenuResourceList = new ArrayList<>();
                    int[] childMenuOrderArray = new int[18];
                    int index2 = 0;
                    for (Resource resource1 : sourceList) {
                        if (resource1.getParentId().equals(parentId)) {
                            childMenuResourceSortList.add(resource1);
                            //得到当前父菜单的子菜单的顺序order数组
                            childMenuOrderArray[index2] = resource1.getOrder();
                            index2++;
                        }
                    }
                    //对子菜单order数组排序
                    int[] childIdSortArray = menuIdSort(childMenuOrderArray);
                    for (int j = 0; j < childIdSortArray.length; j++) {
                        if (childIdSortArray[j] == 0) {
                            continue;
                        }
                        for (Resource resource1 : childMenuResourceSortList) {
                            if (resource1.getOrder() == childIdSortArray[j]) {
                                childMenuResourceList.add(resource1);
                            }
                        }
                    }
                    stringObjectMap.put("children", childMenuResourceList);
                    mapList.add(stringObjectMap);
                }
            }
        }
        return mapList;
    }

    /**
     * 菜单数组排序
     */
    private int[] menuIdSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {//外层循环控制排序趟数
            for (int j = 0; j < array.length - 1 - i; j++) {//内层循环控制每一趟排序多少次
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }
}
