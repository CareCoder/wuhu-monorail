package net.cdsunrise.wm.schedule.vo;

import lombok.Data;
import net.cdsunrise.wm.schedule.entity.SchedulePlan;

import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 进度编制确认审核组合信息
 */
@Data
public class SchedulePlanCombineAuditVo {
    private SchedulePlan schedulePlan;
    private List<ScheduleAuditVo> auditHisList=new ArrayList<>();
}
