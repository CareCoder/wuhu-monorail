package net.cdsunrise.wm.quality.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import net.cdsunrise.wm.quality.vo.RepositoryGroupVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 梁墩标准数据
 */
@Data
@Entity
@Table(name="wm_beam_pier_standard")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class BeamPierStandard extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "beam_standard_id")
    @JsonBackReference
    private BeamStandard beamStandard;
    /***
     * 墩编号
     */
    private String pierCode;
    /***
     * 墩里程端大小
     * 1 - 小
     * 2 - 大
     */
    private int pierSize;

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

    @OneToMany(mappedBy = "pierStandard",cascade = CascadeType.ALL)
    private List<BeamPierSupportStandard> supportStandardList=new ArrayList<>();

    public void addSupportStandard(BeamPierSupportStandard supportStandard){
        supportStandardList.add(supportStandard);
        supportStandard.setPierStandard(this);
    }

    /***
     * 测量人ID
     */
    private Long measureUserId;
    /***
     * 测量人姓名
     */
    private String measureUserName;

    @Transient
    private BeamPierStandard inverse;

    @Override
    public String toString() {
        return "BeamPierStandard{" +
                "pierCode='" + pierCode + '\'' +
                ", pierSize=" + pierSize +
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
    private void initLastModifyHis(RepositoryGroupVo repositoryGroupVo){
        lastModifyHis = repositoryGroupVo.getSectionStandardModifyHisRepository().findForModifyCheck(this.id,"PIER");
    }
    public boolean validate(BigDecimal pow,RepositoryGroupVo repositoryGroupVo){
        boolean supportResult = true;
        for(BeamPierSupportStandard pierSupportStandard:supportStandardList){
            boolean supportResultTemp = pierSupportStandard.validate(pow,repositoryGroupVo);
            if(!supportResultTemp){
                supportResult = false;
                initLastModifyHis(repositoryGroupVo);
            }
        }
        if(topWidthDeviation==null||Math.abs(topWidthDeviation.multiply(pow).intValue())<widthStandard){
            if(topWidthDeviation!=null&&inverse!=null&&inverse.getTopWidthDeviation()!=null){
                boolean inverseOk = Math.abs(topWidthDeviation.subtract(inverse.getTopWidthDeviation()).multiply(pow).intValue())<widthStandard;
                if(!inverseOk){
                    initLastModifyHis(repositoryGroupVo);
                    return false;
                }
            }
            if(bottomWidthDeviation==null||Math.abs(bottomWidthDeviation.multiply(pow).intValue())<widthStandard){
                if(bottomWidthDeviation!=null&&inverse!=null&&inverse.getBottomWidthDeviation()!=null){
                    boolean inverseOk = Math.abs(bottomWidthDeviation.subtract(inverse.getBottomWidthDeviation()).multiply(pow).intValue())<widthStandard;
                    if(!inverseOk){
                        initLastModifyHis(repositoryGroupVo);
                        return false;
                    }
                }
                if(leftHeightDeviation==null||Math.abs(leftHeightDeviation.multiply(pow).intValue())<heightStandard){
                    if(leftHeightDeviation!=null&&inverse!=null&&inverse.getLeftHeightDeviation()!=null){
                        boolean inverseOk = Math.abs(leftHeightDeviation.subtract(inverse.getLeftHeightDeviation()).multiply(pow).intValue())<heightStandard;
                        if(!inverseOk){
                            initLastModifyHis(repositoryGroupVo);
                            return false;
                        }
                    }
                    boolean result = rightHeightDeviation==null||Math.abs(rightHeightDeviation.multiply(pow).intValue())<heightStandard;
                    if(result){
                        if(rightHeightDeviation!=null&&inverse!=null&&inverse.getRightHeightDeviation()!=null){
                            boolean inverseOk = Math.abs(rightHeightDeviation.subtract(inverse.getRightHeightDeviation()).multiply(pow).intValue())<heightStandard;
                            if(!inverseOk){
                                initLastModifyHis(repositoryGroupVo);
                                return false;
                            }
                        }
                    }
                    return result&&supportResult;
                }
            }
        }
        initLastModifyHis(repositoryGroupVo);
        return false;
    }
}
