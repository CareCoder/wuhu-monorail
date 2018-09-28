package net.cdsunrise.wm.prophase.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :房屋拆迁
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wm_house_demolition")
public class HouseDemolition extends BaseEntity {

    @ApiParam("与车站关系")
    private String relationship;

    @ApiParam("占地拆迁原因")
    private String demolitionReason;

    @ApiParam("建筑物性质")
    private String nature;

    @ApiParam("层数")
    private int layerNumber;

    @ApiParam("拆迁面积")
    private String demolitionArea;

    @ApiParam("拆迁状态：未拆迁，拆迁中，已拆迁")
    private String demolitionStatus;

    @ApiParam("是否还建")
    private String isConBuild;

    @ApiParam("占用时间/天")
    private String occupyTime;

    @ApiParam("拆迁难度")
    private String demolitionDifficulty;

    @ApiParam("产权")
    private String propertyRight;

    @ApiParam("紧迫性")
    private String urgency;

    @OneToMany(mappedBy = "houseDl",cascade = CascadeType.ALL)
    private List<HouseDemolitionRefModel> refModelList;

    @Override
    public String toString() {
        return "HouseDemolition{" +
                "relationship='" + relationship + '\'' +
                ", demolitionReason='" + demolitionReason + '\'' +
                ", nature='" + nature + '\'' +
                ", layerNumber=" + layerNumber +
                ", demolitionArea='" + demolitionArea + '\'' +
                ", demolitionStatus='" + demolitionStatus + '\'' +
                ", isConBuild='" + isConBuild + '\'' +
                ", occupyTime='" + occupyTime + '\'' +
                ", demolitionDifficulty='" + demolitionDifficulty + '\'' +
                ", propertyRight='" + propertyRight + '\'' +
                ", urgency='" + urgency + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
