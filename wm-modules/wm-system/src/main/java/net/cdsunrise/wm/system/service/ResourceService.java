package net.cdsunrise.wm.system.service;

import net.cdsunrise.wm.system.entity.Resource;
import net.cdsunrise.wm.system.vo.ResourcesPathVo;
import net.cdsunrise.wm.system.vo.ResourcesVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author : WangRui
 * Date : 2018/4/24
 * Describe :
 */
public interface ResourceService {

    List<Resource> findAll();

    List<Resource> findUserResources(Long userId);

    List<Map<String,Object>> lookUpUserRight();

    List<String> lookUpUserRightPath(Long id);

    List<Map<String,Object>> allMenuResources();

    List<Map<String,Object>> userRoleRight(Long id);

    Resource fetchUserType(String code);
}
