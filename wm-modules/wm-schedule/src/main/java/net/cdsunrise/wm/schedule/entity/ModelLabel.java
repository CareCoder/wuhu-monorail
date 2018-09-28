package net.cdsunrise.wm.schedule.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 模型标签
 */
@Data
@Entity
@Table(name = "wm_model_label")
public class ModelLabel extends BaseEntity {
    private Long createUserId;
    private String content;
    private String title;
    private Long modelId;
}
