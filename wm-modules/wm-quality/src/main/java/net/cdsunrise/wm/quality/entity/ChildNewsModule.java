package net.cdsunrise.wm.quality.entity;

import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @author yq
 * 新闻子模块
 */
@Data
@Entity
@Table(name = "wm_child_news_module")
@DynamicUpdate
public class ChildNewsModule extends BaseEntity {
    /**
     * 子模块名字
     */
    private String mName;

    /**
     * 父模块id
     */
    private Long pid;

    /**
     * 模块的img地址
     */
    private String mImg;

    /**
     * 模块的内容
     */
    private String content;

    /**
     * 模块内容的标题
     */
    private String contentTitle;
}
