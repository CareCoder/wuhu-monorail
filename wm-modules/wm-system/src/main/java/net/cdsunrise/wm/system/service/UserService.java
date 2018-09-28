package net.cdsunrise.wm.system.service;

import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.system.entity.User;
import net.cdsunrise.wm.system.vo.UserUpdateVo;
import net.cdsunrise.wm.system.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
public interface UserService {

    void save(UserVo userVo);

    void delete(Long id);

    UserVo findOne(Long id);

    User findOriginalUser(Long id);

    Pager<UserVo> getPager(PageCondition condition);

    List<UserVo> selectList(UserVo userVo);

    List<UserVo> findByIds(List<Long> ids);

    void update(UserVo userVo);

    void resetPassword(Long id);

    void cancelActivate(Long id, String status);

    List<UserVo> selectByName(String username);

    List<UserVo> userSubordinate(Long id);

    String userModifyPs(Long id,String newPassword,String oldPassword);

    List<UserVo> deptAllUser(Long id);

    Map<String, Object> findByUsername(String username);

    void updateUserByUser(UserUpdateVo userUpdateVo);
}
