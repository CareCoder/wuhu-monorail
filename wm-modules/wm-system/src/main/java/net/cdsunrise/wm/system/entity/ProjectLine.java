package net.cdsunrise.wm.system.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author gechaoqing
 * 项目总览-线路
 */
@Data
@Entity
@Table(name = "wm_project_line")
public class ProjectLine extends BaseEntity{
    private String name;
    private String code;
    private String summary;
}
