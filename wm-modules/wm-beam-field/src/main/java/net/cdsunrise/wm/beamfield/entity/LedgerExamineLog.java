package net.cdsunrise.wm.beamfield.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Author: RoronoaZoro丶WangRUi
 * Time: 2018/6/15/015
 * Describe:
 */
@Data
@Entity
@Table(name = "wm_ledger_examine_log")
public class LedgerExamineLog extends BaseEntity {

    @ApiParam("梁号")
    private String beamNumber;

    @ApiParam("审核工序id")
    private Integer processExamineId;

    @ApiParam("意见")
    @Column(columnDefinition = "TEXT")
    private String advice;

    @ApiParam("填报人")
    private String reportUser;

    @ApiParam("填报时间")
    private Date reportTime;
}
