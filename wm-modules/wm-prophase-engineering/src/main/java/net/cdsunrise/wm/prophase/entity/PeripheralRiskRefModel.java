package net.cdsunrise.wm.prophase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.*;

/**
 * Author: WangRui
 * Date: 2018/5/21
 * Describe:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "wm_peripheral_risk_ref_model")
public class PeripheralRiskRefModel extends BaseEntity {

    @ApiParam("周边风险数据ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peripheral_risk_id")
    @JsonIgnore
    private PeripheralRisk peripheralRisk;

    @ApiParam("模型GUID")
    private String guid;

    @ApiParam("模型FID")
    private Long fid;

    @ApiParam("model  id")
    private Long modelId;
}
