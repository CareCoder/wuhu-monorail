package net.cdsunrise.wm.schedule.vo;

import lombok.Data;

import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 工程快速计量
 */
@Data
public class QuantityMeasurementVo {
    /***
     * 项目编号
     */
    private String code;
    /***
     * 项目名称
     */
    private String name;
    /***
     * 计量单位
     */
    private String unit;
    /***
     * 工程量
     */
    private BigDecimal quantity;
}
