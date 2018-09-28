package net.cdsunrise.wm.schedule.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author geray
 * 进度延迟因素
 */
@Data
@Entity
@Table(name="wm_delay_reason")
public class DelayReason extends BaseEntity {
    private Long parentId;
    private String name;
}
