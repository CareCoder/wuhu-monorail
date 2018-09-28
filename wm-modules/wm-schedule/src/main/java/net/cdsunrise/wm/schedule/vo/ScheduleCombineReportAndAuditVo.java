package net.cdsunrise.wm.schedule.vo;

import lombok.Data;
import net.cdsunrise.wm.schedule.entity.SchedulePlan;

import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 组合上报历史和审核历史的进度计划
 */
@Data
public class ScheduleCombineReportAndAuditVo {
    private SchedulePlan schedulePlan;
    private List<ScheduleReportVo> reportList=new ArrayList<>();
    private List<ScheduleAuditVo> auditHisList=new ArrayList<>();
}
