package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: WangRui
 * Time: 2018/6/3/003
 * Describe:
 */
@Data
public class DrawingQueryVo {

    @ApiParam("梁型")
    private String beamType;

    @ApiParam("墩高")
    private String pierHeight;
}