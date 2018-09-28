package net.cdsunrise.wm.quality.vo;

import lombok.Data;

/***
 * @author gechaoqing
 * 要更新的偏差值
 */
@Data
public class PierDisStandardDeviationVo {
    private Long id;
    private Double flatDeviation;
    private Double heightDeviation;
}
