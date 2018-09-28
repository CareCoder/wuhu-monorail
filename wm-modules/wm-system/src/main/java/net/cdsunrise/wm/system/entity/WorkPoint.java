package net.cdsunrise.wm.system.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 项目线路下的工点/区间
 */
@Data
@Entity
@Table(name = "wm_project_work_point")
public class WorkPoint extends BaseEntity {
    private String name;
    private String code;
    private String type;
    private Long lineId;
    private String bimLookAt;
}
