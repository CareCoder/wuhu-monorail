package net.cdsunrise.wm.quality.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author yq
 * 新闻模块
 */
@Data
@Entity
@Table(name = "wm_news_module")
@DynamicUpdate
public class NewsModule extends BaseEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
}
