package net.cdsunrise.wm.quality.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import net.cdsunrise.wm.quality.vo.RepositoryGroupVo;

import javax.persistence.*;
import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 梁断面标准信息
 */
@Data
@Entity
@Table(name = "wm_beam_section_standard")
public class BeamSectionStandard extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "beam_standard_id")
    @JsonBackReference
    private BeamStandard beamStandard;
    private String sectionType;
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal topWidth;
    /***
     * 顶面宽度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal topWidthDeviation;

    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal bottomWidth;
    /***
     * 底面宽度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal bottomWidthDeviation;

    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal leftHeight;
    /***
     * 左侧高度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal leftHeightDeviation;

    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal rightHeight;
    /***
     * 右侧高度偏差
     */
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal rightHeightDeviation;

    /***
     * 测量人ID
     */
    private Long measureUserId;
    /***
     * 测量人姓名
     */
    private String measureUserName;
    @Override
    public String toString() {
        return "BeamSectionStandard{" +
                "sectionType='" + sectionType + '\'' +
                ", topWidth=" + topWidth +
                ", topWidthDeviation=" + topWidthDeviation +
                ", bottomWidth=" + bottomWidth +
                ", bottomWidthDeviation=" + bottomWidthDeviation +
                ", leftHeight=" + leftHeight +
                ", leftHeightDeviation=" + leftHeightDeviation +
                ", rightHeight=" + rightHeight +
                ", rightHeightDeviation=" + rightHeightDeviation +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }

    @Transient
    private final int widthStandard = 2;
    @Transient
    private final int heightStandard = 10;
    @Transient
    private BeamSectionStandardModifyHis lastModifyHis;
    public boolean validate(BigDecimal pow,RepositoryGroupVo repositoryGroupVo){
        boolean isOk = false;
        if(topWidthDeviation==null||Math.abs(topWidthDeviation.multiply(pow).intValue())<widthStandard){
            if(bottomWidthDeviation==null||Math.abs(bottomWidthDeviation.multiply(pow).intValue())<widthStandard){
                if(leftHeightDeviation==null||Math.abs(leftHeightDeviation.multiply(pow).intValue())<heightStandard){
                    if(rightHeightDeviation==null||Math.abs(rightHeightDeviation.multiply(pow).intValue())<heightStandard){
                        isOk = true;
                    }
                }
            }
        }
        if(!isOk){
            lastModifyHis = repositoryGroupVo.getSectionStandardModifyHisRepository().findForModifyCheck(this.id,"BEAM");
        }
        return isOk;
    }
}
