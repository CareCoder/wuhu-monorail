package net.cdsunrise.wm.quality.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author yq
 * 报告新闻模块
 */
@Data
@Entity
@Table(name = "wm_report_news_module")
@DynamicUpdate
public class ReportNewsModule extends BaseEntity {
    private String title;
    /**
     * 父id
     */
    private Long pid;
    /**
     * 工程名字
     */
    private String projectName;
    /**
     *施工单位
     */
    private String builder;
    /**
     * 监理单位
     */
    private String watcherUnit;
    /**
     * 验收部位
     */
    private String checker;
    /**
     * 项目部技术员
     */
    private String technology;
    /**
     * 技术负责人
     */
    private String technologyFor;
    /**
     * 监理
     */
    private String supervision;
    /**
     * 验收结果
     */
    private String result;
    /**
     * 验收时间
     */
    private String resultTime;
}
