package net.cdsunrise.wm.beamfield.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/13/013
 * Describe:
 */
@Data
public class ProcessEditVo {

    @ApiParam("工序id")
    private Integer pId;

    @ApiParam("标签")
    private String label;

    @ApiParam("输入类型: 1 文本输入框, 2 时间选择框")
    private Integer inputType;

    @ApiParam("字段名称name")
    private String code;
}
