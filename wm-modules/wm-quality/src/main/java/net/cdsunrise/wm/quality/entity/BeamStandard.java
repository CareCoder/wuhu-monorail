package net.cdsunrise.wm.quality.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.quality.vo.RepositoryGroupVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * @author gechaoqing
 * 梁标准信息
 */
@Data
@Entity
@Table(name = "wm_beam_standard")
public class BeamStandard {
    @Id
    private String beamCode;
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal length;

    /***
     * 长度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal lengthDeviation;

    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal height;

    /***
     * 高度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal heightDeviation;

    @OneToMany(mappedBy = "beamStandard",cascade = CascadeType.ALL)
    private List<BeamSectionStandard> sectionStandardList = new ArrayList<>();

    @OneToMany(mappedBy = "beamStandard",cascade = CascadeType.ALL)
    private List<BeamPierStandard> pierStandardList = new ArrayList<>();

    @ApiParam("更新时间")
    protected Date modifyTime;
    /***
     * 标准偏差范围
     */
    @Transient
    private final int standard = 10;
    /***
     * 测量人ID
     */
    private Long measureUserId;
    /***
     * 测量人姓名
     */
    private String measureUserName;

    public void addSectionStandard(BeamSectionStandard sectionStandard){
        sectionStandardList.add(sectionStandard);
        sectionStandard.setBeamStandard(this);
    }

    public void addPierStandard(BeamPierStandard pierStandard){
        pierStandardList.add(pierStandard);
        pierStandard.setBeamStandard(this);
    }

    @Transient
    private boolean isOk;
    @Transient
    private BeamStandardModifyHis lastModifyHis;

    public void validate(RepositoryGroupVo repositoryGroupVo) {
        BigDecimal pow = new BigDecimal(1000);
        boolean step1 = heightDeviation==null||(Math.abs(heightDeviation.multiply(pow).intValue()) < standard);

        boolean step2 = true;
        for (BeamSectionStandard sectionStandard : sectionStandardList) {
            boolean result = sectionStandard.validate(pow, repositoryGroupVo);
            if (!result) {
                step2 = false;
            }
        }
        boolean step3 = true;
        {
            for (BeamPierStandard pierStandard : pierStandardList) {
                boolean result = pierStandard.validate(pow, repositoryGroupVo);
                if (!result) {
                    step3 = false;
                }
            }
        }
        if (lengthDeviation == null|| Math.abs(lengthDeviation.multiply(pow).intValue()) < standard) {
            if (step1 && step2 && step3) {
                setOk(true);
            }
        }
        if (!isOk()) {
            lastModifyHis = repositoryGroupVo.getStandardModifyHisRepository().findForModifyCheck(this.getBeamCode());
        }
    }
}
