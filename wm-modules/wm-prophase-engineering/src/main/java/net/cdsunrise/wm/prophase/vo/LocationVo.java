package net.cdsunrise.wm.prophase.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/7/18/018
 * Describe:
 */
@Data
public class LocationVo {

    @ApiParam("位置:x")
    private String locationX;

    @ApiParam("位置:y")
    private String locationY;

    @ApiParam("位置:z")
    private String locationZ;
}
