package net.cdsunrise.wm.schedule.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 模型数据
 */
@Data
@Entity
@Table(name = "wm_model_data")
public class ModelData extends BaseEntity{
    /***
     * 模型ID
     */
    @ApiParam("模型ID")
    private Long modelId;
    /***
     * 模型数据名称ID
     */
    @ApiParam("对应数据列ID")
    private Long colId;
    /***
     * 数据值
     */
    @ApiParam("数据值")
    private String val;
}
