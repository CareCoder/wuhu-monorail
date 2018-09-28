package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

/***
 * @author gechaoqing
 * 获取进度计划前置任务的查询参数
 */
@Data
public class QuerySchedulePlanFrontParams {
    private Long planId;
    private String rel;
    private String startDateStr;
    private String endDateStr;
}
