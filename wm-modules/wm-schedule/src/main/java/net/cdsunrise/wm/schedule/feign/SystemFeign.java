package net.cdsunrise.wm.schedule.feign;

import net.cdsunrise.wm.schedule.feign.fallback.SystemFeignFallback;
import net.cdsunrise.wm.schedule.vo.DepartmentVo;
import net.cdsunrise.wm.schedule.vo.UserVo;
import net.cdsunrise.wm.schedule.vo.WorkPointVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 系统模块API
 */
@FeignClient(name = "wm-system",fallback = SystemFeignFallback.class)
public interface SystemFeign {
    /***
     * 根据部门ID获取岗位ID
     * @param departId 部门ID
     * @return
     */
    @GetMapping("/getPositionIdListByDepartId")
    List<Long> fetchPositionIdListByDepartId(Long departId);

    /***
     * 根据用户ID获取姓名
     * @return 用户信息
     */
    @GetMapping("/user/userInfo")
    UserVo fetchCurrentUser();

    /***
     * 获取当前登录用户参建单位ID
     * @return 参建单位ID
     */
    @GetMapping("/user/userCompanyId")
    Long fetchCurrentUserCompanyId();

    /***
     * 获取当前登录用户所在部门ID
     * @return
     */
    @GetMapping("/user/userDeptId")
    Long fetchCurrentUserDeptId();
    /***
     * 根据用户ID列表获取用户信息列表
     * @param ids 用户ID列表
     * @return 用户信息列表
     */
    @PostMapping("/user/list")
    List<UserVo> fetchUserListByUserIdList(ArrayList<Long> ids);

    /***
     * 根据线路获取工点信息
     * @param lineId 线路ID
     * @return 工点信息
     */
    @GetMapping("/project/work-point/line/{lineId}")
    List<WorkPointVo> fetchWorkPointByLine(@PathVariable("lineId") Long lineId);

    /***
     * 根据工点ID获取工点信息
     * @param id 工点ID
     * @return
     */
    @GetMapping("/project/work-point/{id}")
    WorkPointVo fetchWorkPointById(@PathVariable("id")Long id);

    /***
     * 获取当前登录用户的子部门ID集合
     * @return ID集合
     */
    @GetMapping("/dept/subordinateDepartment")
    List<Long> subDepartId();

    /***
     * 获取指定部门的子部门ID集合
     * @param id 部门ID
     * @return ID集合
     */
    @GetMapping("/dept/subordinateDepartmentByDeptId/{id}")
    List<Long> subDepartId(@PathVariable("id") Long id);

    /***
     * 获取所有部门信息
     * @return
     */
    @GetMapping("/dept/list")
    List<DepartmentVo> getAllDepartment();
}
