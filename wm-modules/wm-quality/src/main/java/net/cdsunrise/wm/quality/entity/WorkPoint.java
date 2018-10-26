package net.cdsunrise.wm.quality.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author yq
 * 车间站点和工作站点的对应关系, 一对多
 */
@Data
@Entity
@Table(name = "wm_car_work_point")
@DynamicUpdate
public class CarAndWorkPoint extends BaseEntity {
    /**
     * 车间站点id
     */
    private Long carPointId;
    /**
     * 工作站点id
     */
    private Long workPointId;
    /**
     * 工作站的名字
     */
    private String name;
}
