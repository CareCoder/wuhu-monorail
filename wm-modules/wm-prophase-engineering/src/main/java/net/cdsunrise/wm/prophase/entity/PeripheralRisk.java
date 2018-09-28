package net.cdsunrise.wm.prophase.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/17
 * Describe :周边风险
 */
@Data
@Entity
@Table(name = "wm_peripheral_risk")
public class PeripheralRisk extends BaseEntity {

    @ApiParam("名称")
    private String name;

    @ApiParam("类型：建筑物，管线，桥梁，道路，河湖，铁路，既有线，高压线塔，其他")
    private String type;

    @ApiParam("风险等级：特级，一级，二级，三级")
    private String riskLevel;

    @ApiParam("通过状态:未通过，通过中，已通过")
    private String passStatus;

    @ApiParam("与地铁位置关系")
    private String positionalRelation;

    @ApiParam("开挖边线与基础水平距离")
    private String sideLineDistance;

    @ApiParam("拱顶与基础垂直距离")
    private String vaultHorizonDis;

    @ApiParam("详细描述")
    private String detailedDescription;

    @ApiParam("修建时间")
    private Date buildTime;

    @ApiParam("产权单位")
    private String propertyCompany;

    @ApiParam("结构形式")
    private String structuralStyle;

    @ApiParam("层数")
    private int layerNumber;

    @ApiParam("底板标高")
    private String floorElevation;

    @ApiParam("基础型式")
    private String foundationType;

    @ApiParam("尺寸")
    private String size;

    @ApiParam("桩径")
    private String pileDiameter;

    @ApiParam("桩基总数")
    private int pileNumber;

    @ApiParam("起点里程")
    private String startMileage;

    @ApiParam("终点里程")
    private String endMileage;

    @ApiParam("基础参数")
    private String basicParameters;

    @ApiParam("道理等级")
    private String dLevel;

    @ApiParam("管线性质")
    private String pileNature;

    @ApiParam("管线尺寸")
    private String pileSize;

    @ApiParam("管线壁厚")
    private String pileWallThickness;

    @ApiParam("管线施工方法")
    private String pileMethod;

    @ApiParam("防渗处理工艺")
    private String antiSeepageTret;

    @ApiParam("拱顶埋深")
    private String vaultBuriedDepth;

    @ApiParam("通过时间")
    private Date passTime;

    @ApiParam("综合安全状态")
    private String securityState;

    @ApiParam("工程地质")
    private String engineGeology;

    @ApiParam("工程措施")
    private String engineMeasures;

    @ApiParam("备注")
    private String note;

    @OneToMany(mappedBy = "peripheralRisk",cascade = CascadeType.ALL)
    private List<PeripheralRiskRefModel> refModelList;

    @Override
    public String toString() {
        return "PeripheralRisk{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", passStatus='" + passStatus + '\'' +
                ", positionalRelation='" + positionalRelation + '\'' +
                ", sideLineDistance='" + sideLineDistance + '\'' +
                ", vaultHorizonDis='" + vaultHorizonDis + '\'' +
                ", detailedDescription='" + detailedDescription + '\'' +
                ", buildTime=" + buildTime +
                ", propertyCompany='" + propertyCompany + '\'' +
                ", structuralStyle='" + structuralStyle + '\'' +
                ", layerNumber=" + layerNumber +
                ", floorElevation='" + floorElevation + '\'' +
                ", foundationType='" + foundationType + '\'' +
                ", size='" + size + '\'' +
                ", pileDiameter='" + pileDiameter + '\'' +
                ", pileNumber=" + pileNumber +
                ", startMileage='" + startMileage + '\'' +
                ", endMileage='" + endMileage + '\'' +
                ", basicParameters='" + basicParameters + '\'' +
                ", dLevel='" + dLevel + '\'' +
                ", pileNature='" + pileNature + '\'' +
                ", pileSize='" + pileSize + '\'' +
                ", pileWallThickness='" + pileWallThickness + '\'' +
                ", pileMethod='" + pileMethod + '\'' +
                ", antiSeepageTret='" + antiSeepageTret + '\'' +
                ", vaultBuriedDepth='" + vaultBuriedDepth + '\'' +
                ", passTime=" + passTime +
                ", securityState='" + securityState + '\'' +
                ", engineGeology='" + engineGeology + '\'' +
                ", engineMeasures='" + engineMeasures + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
