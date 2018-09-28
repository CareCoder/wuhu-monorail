package net.cdsunrise.wm.quality.vo;

import lombok.Data;

import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 梁综合参数信息偏差数据
 */
@Data
public class BeamDeviationsVo {
    private String beamCode;
    private BigDecimal lengthDeviation;
    private BigDecimal heightDeviation;
    private boolean modify;
}
