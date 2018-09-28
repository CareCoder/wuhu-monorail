package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 进度上报关联模型工程量实体
 */
@Data
public class ScheduleReportRefModelVo {
    /***
     * 工程量ID
     */
    private Long projectVolumeId;
    /***
     * 已上报工程量
     */
    private BigDecimal reportedQuantity;
    /***
     * 计划工程量
     */
    private BigDecimal planQuantity;
    /***
     * 工程量名称
     */
    private String name;
}
