package net.cdsunrise.wm.system.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.exception.DataBaseRollBackException;
import net.cdsunrise.wm.base.exception.ServiceErrorException;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.system.entity.Position;
import net.cdsunrise.wm.system.entity.Role;
import net.cdsunrise.wm.system.entity.User;
import net.cdsunrise.wm.system.repository.UserRepository;
import net.cdsunrise.wm.system.service.UserService;
import net.cdsunrise.wm.system.vo.UserUpdateVo;
import net.cdsunrise.wm.system.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
@Transactional(rollbackFor = {DataBaseRollBackException.class})
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${userPassword}")
    private String userPassword;

    @Override
    public void save(UserVo userVo) {
        //判断用户名是否为空
        if (StringUtils.isBlank(userVo.getUsername())) {
            throw new ServiceErrorException("用户名不能为空！");
        }
        User u = userRepository.findByUsername(userVo.getUsername());
        if (u != null) {
            throw new ServiceErrorException("已存在相同用户名！");
        } else {
            User creator = userRepository.findOne(UserUtils.getUserId());
            User user = new User();
            BeanUtils.copyProperties(userVo, user);
            user.setPassword(bCryptPasswordEncoder.encode(userPassword));
            Position position = new Position();
            position.setId(userVo.getPositionId());
            user.setPosition(position);
            Role role = new Role();
            role.setId(userVo.getRoleId());
            user.setRole(role);
            user.setStatus(1);
            user.setUserAuthorId(creator.getPosition().getDepartment().getUserAuthorId());
            userRepository.save(user);
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public UserVo findOne(Long id) {
        User u = userRepository.findOne(id);
        return convertToVo(u);
    }

    @Override
    public User findOriginalUser(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Pager<UserVo> getPager(PageCondition condition) {
        QueryHelper helper = new QueryHelper(User.class, "l")
                .addCondition("status <> 0")
                .addCondition(" userAuthorId = ?",userRepository.findOne(UserUtils.getUserId()).getPosition().getDepartment().getUserAuthorId())
                .useNativeSql(false)
                .setPageCondition(condition);
        Pager<User> pager = commonDAO.findPager(helper);
        Pager<UserVo> p = new Pager<>(pager.getNumber(), pager.getPageSize(), pager.getTotalElements(), convertToVos(pager.getContent()));
        return p;
    }

    @Override
    public List<UserVo> selectList(UserVo userVo) {
        QueryHelper helper = new QueryHelper(User.class)
                .addOrCondition("status <> 0")
                .addCondition(" userAuthorId = ?",userRepository.findOne(UserUtils.getUserId()).getUserAuthorId())
                .addCondition(StringUtils.isNotBlank(userVo.getUsername()), "username like ?", userVo.getUsername())
                .addCondition(StringUtils.isNotBlank(userVo.getRealName()), "realName like ?", "%" + userVo.getRealName() + "%")
                .addCondition(StringUtils.isNotBlank(userVo.getNickName()), "nickName like ?", "%" + userVo.getNickName() + "%")
                .addCondition(userVo.getPositionId() != null, "positionId = ?", userVo.getPositionId())
                .useNativeSql(false);
        List<User> users = commonDAO.findList(helper);
        return convertToVos(users);
    }

    @Override
    public List<UserVo> findByIds(List<Long> ids) {
        List<User> users = userRepository.findByIdIn(ids);
        return convertToVos(users);
    }

    @Override
    public void update(UserVo userVo) {
        User uu = userRepository.findOne(userVo.getId());
        BeanUtils.copyProperties(userVo, uu,"status");
        Position position = new Position();
        position.setId(userVo.getPositionId());
        uu.setPosition(position);
        Role role = new Role();
        role.setId(userVo.getRoleId());
        uu.setRole(role);
        userRepository.save(uu);
    }

    /**
     * 密码重置
     *
     * @param id
     */
    @Override
    public void resetPassword(Long id) {
        User user = userRepository.findOne(id);
        user.setPassword(bCryptPasswordEncoder.encode(userPassword));
        userRepository.save(user);
    }

    /**
     * 用户激活/注销
     *
     * @param id
     * @param status
     */
    @Override
    public void cancelActivate(Long id, String status) {
        User user = userRepository.findOne(id);
        user.setStatus(status.equals("激活") ? 2 : 1);
        userRepository.save(user);
    }

    @Override
    public List<UserVo> selectByName(String username) {
        UserVo userVo = new UserVo();
        userVo.setRealName(username);
        return selectList(userVo);
    }

    /**
     * 查询当前用户所有下属
     *
     * @param id
     * @return
     */
    @Override
    public List<UserVo> userSubordinate(Long id) {
        QueryHelper helper = new QueryHelper("w.id,w.real_name as realName", "wm_user w")
                .addCondition("position_id in (select id from wm_position where parent_id = (SELECT position_id FROM wm_user where id=?))", id)
                .useNativeSql(true);
        List<UserVo> list = commonDAO.findList(helper, UserVo.class);
        return list;
    }

    /**
     * 用户修改密码
     *
     * @param id
     * @param newPassword
     * @param oldPassword
     * @return
     */
    @Override
    public String userModifyPs(Long id, String newPassword, String oldPassword) {
        User user = userRepository.findOne(id);
        if (!bCryptPasswordEncoder.matches(oldPassword,user.getPassword())) {
            return "旧密码输入错误！";
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return "密码修改成功！";
        }
    }

    /**
     * 查询当前用户所在部门所有员工
     *
     * @param id
     * @return
     */
    @Override
    public List<UserVo> deptAllUser(Long id) {
        Long deptId = userRepository.findOne(id).getPosition().getDepartment().getId();
        List<User> users = userRepository.findAll();
        List<UserVo> userVos = convertToVos(users);
        UserVo userVo;
        for (int i = 0;i<userVos.size();i++){
            userVo = userVos.get(i);
            if (userVo.getDeptId()!=deptId){
                userVos.remove(i);
            }
        }
        return userVos;
    }

    @Override
    public Map<String, Object> findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        Map<String, Object> userMap = new HashMap<>(3);
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("password", user.getPassword());
        return userMap;
    }

    @Override
    public void updateUserByUser(UserUpdateVo userUpdateVo) {
        User uu = userRepository.findOne(userUpdateVo.getId());
        BeanUtils.copyProperties(userUpdateVo, uu);
        userRepository.save(uu);
    }


    /**
     * User对象转换成UserVo对象
     *
     * @param users
     * @return
     */
    private List<UserVo> convertToVos(List<User> users) {
        if (users.size()>0){
            List<UserVo> vos = new ArrayList<>();
            users.forEach(user ->
                    vos.add(convertToVo(user))
            );
            return vos;
        }else {
            return null;
        }
    }

    /**
     * User对象转换成UserVo对象
     *
     * @param u
     * @return
     */
    private UserVo convertToVo(User u) {
        if (u!=null){
            UserVo vo = new UserVo();
            BeanUtils.copyProperties(u, vo);
            vo.setRoleId(u.getRole().getId());
            vo.setRoleName(u.getRole().getName());
            vo.setPositionId(u.getPosition().getId());
            vo.setPositionName(u.getPosition().getName());
            vo.setDeptId(u.getPosition().getDepartment().getId());
            vo.setDeptName(u.getPosition().getDepartment().getName());
            vo.setStatus(u.getStatus()==1?"激活":"注销");
            return vo;
        }else {
            return null;
        }
    }

}
