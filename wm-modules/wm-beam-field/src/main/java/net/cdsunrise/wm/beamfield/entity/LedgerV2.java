package net.cdsunrise.wm.beamfield.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Author: WangRui
 * Date: 2018/6/7
 * Describe:
 */
@Data
@Entity
@Table(name = "wm_ledger2")
public class LedgerV2 extends BaseEntity {

    @ApiParam("梁号")
    @OneToOne
    @JoinColumn(name = "beam_number")
    private LedgerBasicInformation ledgerBasicInformation;

    @ApiParam("是否下发")
    private String isGrant = "已下发";

    @ApiParam("制梁台座")
    private String beamPedestal;

    @ApiParam("钢筋绑扎完成时间")
    private String steelBarBindingTime;

    @ApiParam("合模时间")
    private String modeTime;

    @ApiParam("浇筑时间")
    private String pouringTime;

    @ApiParam("坍落度")
    private String slump;

    @ApiParam("入模温度")
    private String dieTemperature;

    @ApiParam("拆模时间")
    private String dieBreakingTime;

    @ApiParam("初张拉时间")
    private String initialTensioningTime;

    @ApiParam("移梁时间")
    private String beamShiftingTime;

    @ApiParam("存梁台座号")
    private String storageBeamNumber;

    @ApiParam("终张拉时间")
    private String endTensioningTime;

    @ApiParam("压浆时间")
    private String pulpingTime;

    @ApiParam("是否运出梁场")
    private String isTransport = "否";
}
