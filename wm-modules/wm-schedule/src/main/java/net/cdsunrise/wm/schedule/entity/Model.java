package net.cdsunrise.wm.schedule.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 模型
 */
@Data
@Entity
@Table(name = "wm_model")
public class Model extends BaseEntity {
    private String uniqueId;
    private Long categoryId;
    /**{@link net.cdsunrise.wm.schedule.enums.ModelStatus}*/
    private Integer modelStatus;
}
