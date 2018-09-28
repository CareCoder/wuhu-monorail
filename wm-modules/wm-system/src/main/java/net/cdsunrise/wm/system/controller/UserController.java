package net.cdsunrise.wm.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.base.web.annotation.*;
import net.cdsunrise.wm.system.entity.Resource;
import net.cdsunrise.wm.system.service.ResourceService;
import net.cdsunrise.wm.system.service.UserService;
import net.cdsunrise.wm.system.vo.UserUpdateVo;
import net.cdsunrise.wm.system.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe : 用户模块Controller
 */
@Api(value = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "新增一个用户")
    @SavePath
    public void save(UserVo userVo) {
        userService.save(userVo);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping(value = "/updateUser")
    public void update(UserVo userVo) {
        userService.update(userVo);
    }

    @ApiOperation(value = "用户修改自己的部分信息")
    @PostMapping(value = "/updateUserByUser")
    public void update(UserUpdateVo userUpdateVo) {
        userService.updateUserByUser(userUpdateVo);
    }

    @ApiOperation(value = "删除")
    @DeletePath
    public void delete(Long id) {
        userService.delete(id);
    }

    @ApiOperation(value = "查询单个信息")
    @GetPath
    public UserVo get(Long id) {
        return userService.findOne(id);
    }

    /**
     * 返回当前用户的单位id
     * @return
     */
    @GetMapping("/userCompanyId")
    public Long userCompanyId(){
        return userService.findOriginalUser(UserUtils.getUserId()).getPosition().getDepartment().getCompany().getId();
    }

    @ApiOperation(value = "根据id数组批量查询")
    @PostMapping("/list")
    public List<UserVo> getList(ArrayList<Long> ids) {
        return userService.findByIds(ids);
    }

    @ApiOperation(value = "分页")
    @PagePath
    public Pager<UserVo> getPager(PageCondition condition) {
        return userService.getPager(condition);
    }

    @ApiOperation("重置密码")
    @GetMapping(value = "/resetPassword")
    public void resetPassword(Long id) {
        userService.resetPassword(id);
    }

    @ApiOperation("注销/激活")
    @GetMapping(value = "/cancelActivate")
    public void cancelActivate(Long id, String status) {
        userService.cancelActivate(id, status);
    }

    @ApiOperation(value = "根据用户名精确查询")
    @GetMapping("/findByUsername")
    public Map<String, Object> findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @ApiOperation("用户姓名模糊查询")
    @GetMapping(value = "/selectByName")
    public List<UserVo> selectByName(String userRealName) {
        return userService.selectByName(userRealName);
    }

    @ApiOperation("查看当前登录用户菜单详细信息列表")
    @GetMapping(value = "/lookUpUserRight")
    public List<Map<String,Object>> lookUpUserRight() {
        return resourceService.lookUpUserRight();
    }

    @ApiOperation("查看当前登录用户菜单path列表")
    @GetMapping(value = "/lookUpUserRightPath")
    public List<String> lookUpUserRightPath() {
        UserVo userVo = userService.findOne(UserUtils.getUserId());
        return resourceService.lookUpUserRightPath(userVo.getRoleId());
    }

    @ApiOperation("查询当前用户的所有下属")
    @GetMapping("/userSubordinate")
    public List<UserVo> userSubordinate() {
        return userService.userSubordinate(UserUtils.getUserId());
    }

    @ApiOperation("查询当前用户所在部门的所有员工")
    @GetMapping("/deptAllUser")
    public List<UserVo> deptAllUser() {
        return userService.deptAllUser(UserUtils.getUserId());
    }

    @ApiOperation("查询当前用户的信息")
    @GetMapping("/userInfo")
    public UserVo userInfo() {
        return userService.findOne(UserUtils.getUserId());
    }

    @ApiOperation("用户修改密码")
    @PostMapping("/userModifyPs")
    public String userModifyPs(String newPassword, String oldPassword) {
        return userService.userModifyPs(UserUtils.getUserId(), newPassword, oldPassword);
    }

    @ApiOperation("查看用户权限")
    @GetMapping("/userRoleRight")
    public List<Map<String,Object>> userRoleRight(Long id){
       return resourceService.userRoleRight(id);
    }

    @ApiOperation("获取当前用户类型(制梁计划列表数据控制)")
    @GetMapping("/fetchUserType")
    public Resource fetchUserType(String code){
        return resourceService.fetchUserType(code);
    }

    @ApiOperation("获取当前登录用户的部门id")
    @GetMapping("/userDeptId")
    public Long userDeptId(){
        UserVo user = userService.findOne(UserUtils.getUserId());
        return user.getDeptId();
    }
}
