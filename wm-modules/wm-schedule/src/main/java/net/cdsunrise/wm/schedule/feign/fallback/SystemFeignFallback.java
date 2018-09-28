package net.cdsunrise.wm.schedule.feign.fallback;

import net.cdsunrise.wm.schedule.feign.SystemFeign;
import net.cdsunrise.wm.schedule.vo.DepartmentVo;
import net.cdsunrise.wm.schedule.vo.UserVo;
import net.cdsunrise.wm.schedule.vo.WorkPointVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 系统模块客户端调用失败
 */
public class SystemFeignFallback implements SystemFeign {
    @Override
    public List<Long> fetchPositionIdListByDepartId(Long departId) {
        return new ArrayList<>();
    }

    @Override
    public UserVo fetchCurrentUser() {
        return null;
    }

    @Override
    public Long fetchCurrentUserCompanyId() {
        return null;
    }

    @Override
    public List<UserVo> fetchUserListByUserIdList(ArrayList<Long> userIdList) {
        return new ArrayList<>();
    }

    @Override
    public List<WorkPointVo> fetchWorkPointByLine(Long lineId) {
        return new ArrayList<>();
    }

    @Override
    public WorkPointVo fetchWorkPointById(Long id) {
        return null;
    }

    @Override
    public List<Long> subDepartId() {
        return new ArrayList<>();
    }

    @Override
    public List<Long> subDepartId(Long parentId) {
        return new ArrayList<>();
    }

    @Override
    public Long fetchCurrentUserDeptId() {
        return null;
    }

    @Override
    public List<DepartmentVo> getAllDepartment() {
        return new ArrayList<>();
    }
}
