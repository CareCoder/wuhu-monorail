package net.cdsunrise.wm.quality.vo;

import lombok.Data;

/***
 * @author gechaoqing
 * 坐标偏差
 */
@Data
public class PierCoordinateStandardVo {
    private Long id;
    private Double xDeviation;
    private Double yDeviation;
    private Double zDeviation;
}
