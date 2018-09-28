package net.cdsunrise.wm.quality.vo;

import lombok.Data;

import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 墩临时支撑偏差数据
 */
@Data
public class BeamPierSupportDeviationsVo {
    private Long id;
    private BigDecimal centerDeviationVal;
    private BigDecimal heightDeviationVal;
    private boolean modify;
}
