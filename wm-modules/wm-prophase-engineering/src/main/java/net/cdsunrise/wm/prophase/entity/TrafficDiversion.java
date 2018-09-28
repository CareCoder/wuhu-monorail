package net.cdsunrise.wm.prophase.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :交通导改
 */
@Data
@Entity
@Table(name = "wm_traffic_diversion")
public class TrafficDiversion extends BaseEntity {

    @ApiParam("车站位置")
    private String stationPosition;

    @ApiParam("工期(天)")
    private int schedule;

    @ApiParam("道理等级")
    private String dLevel;

    @ApiParam("速度")
    private String speed;

    @ApiParam("影响范围")
    private String influenceScope;

    @ApiParam("红线")
    private String redLine;

    @ApiParam("路幅")
    private String roadway;

    @ApiParam("机动车道")
    private String driveway;

    @ApiParam("非机动车道及步道")
    private String notDriveway;

    @ApiParam("隔离绿化带宽")
    private String greenBeltWidth;

    @ApiParam("与工程关系")
    private String relation;

    @ApiParam("导改原因")
    private String diversionReason;

    @ApiParam("公交情况描述")
    private String transitDescribe;

    @ApiParam("机动车道宽度")
    private String vehicleWidth;

    @ApiParam("机动车道数量")
    private int vehicleNumber;

    @ApiParam("混合车道宽度")
    private String blendWidth;

    @ApiParam("混合车道数量")
    private int blendNumber;

    @ApiParam("最小转弯半径")
    private String minTurnRadius;

    @ApiParam("道理破复面积")
    private String pfArea;

    @ApiParam("路缘石长")
    private String stoneLength;

    @ApiParam("树池个数")
    private int treePoolNumber;

    @ApiParam("盲道砖面积")
    private String blindRoadArea;

    @ApiParam("导改新建道理面积")
    private String buildArea;

    @ApiParam("导改时间")
    private Date diversionTime;

    @ApiParam("导改难度")
    private String diversionDifficulty;

    @ApiParam("开始时间")
    private Date beginTime;

    @ApiParam("结束时间")
    private Date endTime;

    @ApiParam("负责单位")
    private String responsibleUnit;

    @ApiParam("位置信息")
    private String positionInformation;

    @ApiParam("位置:x")
    private String locationX;

    @ApiParam("位置:y")
    private String locationY;

    @ApiParam("位置:z")
    private String locationZ;

    @ApiParam("备注")
    private String note;

    @OneToMany(mappedBy = "trafficDiversion",cascade = CascadeType.ALL)
    private List<TrafficDiversionRefModel> refModelList;

    @Override
    public String toString() {
        return "TrafficDiversion{" +
                "stationPosition='" + stationPosition + '\'' +
                ", schedule=" + schedule +
                ", dLevel='" + dLevel + '\'' +
                ", speed='" + speed + '\'' +
                ", influenceScope='" + influenceScope + '\'' +
                ", redLine='" + redLine + '\'' +
                ", roadway='" + roadway + '\'' +
                ", driveway='" + driveway + '\'' +
                ", notDriveway='" + notDriveway + '\'' +
                ", greenBeltWidth='" + greenBeltWidth + '\'' +
                ", relation='" + relation + '\'' +
                ", diversionReason='" + diversionReason + '\'' +
                ", transitDescribe='" + transitDescribe + '\'' +
                ", vehicleWidth='" + vehicleWidth + '\'' +
                ", vehicleNumber=" + vehicleNumber +
                ", blendWidth='" + blendWidth + '\'' +
                ", blendNumber=" + blendNumber +
                ", minTurnRadius='" + minTurnRadius + '\'' +
                ", pfArea='" + pfArea + '\'' +
                ", stoneLength='" + stoneLength + '\'' +
                ", treePoolNumber=" + treePoolNumber +
                ", blindRoadArea='" + blindRoadArea + '\'' +
                ", buildArea='" + buildArea + '\'' +
                ", diversionTime=" + diversionTime +
                ", diversionDifficulty='" + diversionDifficulty + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", responsibleUnit='" + responsibleUnit + '\'' +
                ", positionInformation='" + positionInformation + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
