package net.cdsunrise.wm.schedule.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 模型附加字段
 */
@Data
@Entity
@Table(name = "wm_model_data_extra_cell")
public class ModelDataCol extends BaseEntity {
    @ApiParam("字段名称")
    private String name;
    @ApiParam("字段编码")
    private String code;
    /***
     * 字段类型
     * @see net.cdsunrise.wm.schedule.enums.ModelDataExtraCellType#NUMBER
     * @see net.cdsunrise.wm.schedule.enums.ModelDataExtraCellType#STRING
     */
    @ApiParam("字段类型,数字/字符串['NUMBER','STRING']")
    private String type;
}
