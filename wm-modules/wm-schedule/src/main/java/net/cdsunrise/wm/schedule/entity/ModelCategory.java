package net.cdsunrise.wm.schedule.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 模型分类
 */
@Data
@Entity
@Table(name = "wm_model_category")
public class ModelCategory extends BaseEntity {
    @ApiParam("分类名称")
    private String name;
    @ApiParam("分类编码")
    private String code;
}
