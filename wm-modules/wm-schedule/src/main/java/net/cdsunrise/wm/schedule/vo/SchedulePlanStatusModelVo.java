package net.cdsunrise.wm.schedule.vo;

import lombok.Data;
import net.cdsunrise.wm.schedule.enums.SchedulePlanStatus;

/***
 * @author gechaoqing
 * 任务相关模型根据任务状态高亮
 */
@Data
public class SchedulePlanStatusModelVo {
    private Long fid;
    private Long modelId;
    private String guid;
    private String status;
    private int color;
    private int delay;
}
