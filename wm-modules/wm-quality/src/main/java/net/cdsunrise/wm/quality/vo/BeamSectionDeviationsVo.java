package net.cdsunrise.wm.quality.vo;

import lombok.Data;

import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 梁断面偏差数据
 */
@Data
public class BeamSectionDeviationsVo {
    private Long id;
    /***
     * 1 - 墩断面
     * 2 - 梁断面
     */
    private int type;
    private BigDecimal topWidthDeviation;
    private BigDecimal bottomWidthDeviation;
    private BigDecimal leftHeightDeviation;
    private BigDecimal rightHeightDeviation;
    private boolean modify;
}
