package net.cdsunrise.wm.quality.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;
import net.cdsunrise.wm.quality.vo.RepositoryGroupVo;

import javax.persistence.*;
import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 梁、墩支撑标准数据
 */
@Data
@Entity
@Table(name = "wm_beam_pier_support_standard")
public class BeamPierSupportStandard extends BaseEntity {
    public static final String SUPPORT_TYPE_BEAM="SUPPORT_TYPE_BEAM";
    public static final String SUPPORT_TYPE_PIER="SUPPORT_TYPE_PIER";

    @ManyToOne
    @JoinColumn(name = "pier_standard_id")
    @JsonBackReference
    private BeamPierStandard pierStandard;

    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal centerDeviation;
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal heightDeviation;

    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal centerDeviationVal;
    @Column(columnDefinition="decimal(25,3)")
    private BigDecimal heightDeviationVal;
    private String supportType;

    @Transient
    private final int centerStandard = 3;
    @Transient
    private final int elevationStandardMin = -15;
    @Transient
    private final int elevationStandardMax = 30;

    /***
     * 测量人ID
     */
    private Long measureUserId;
    /***
     * 测量人姓名
     */
    private String measureUserName;

    @Transient
    private BeamPierSupportStandardModifyHis lastModifyHis;

    private void initLastModifyHis(RepositoryGroupVo repositoryGroupVo){
        this.lastModifyHis = repositoryGroupVo.getSupportStandardModifyHisRepository().findForModifyCheck(this.id);
    }

    public boolean validate(BigDecimal pow,RepositoryGroupVo repositoryGroupVo){
        if(centerDeviationVal==null||Math.abs(centerDeviationVal.multiply(pow).intValue())<centerStandard){
            if(heightDeviationVal!=null){
                int heightDeviationIntVal = heightDeviationVal.multiply(pow).intValue();
                boolean result = heightDeviationIntVal>elevationStandardMin&&heightDeviationIntVal<elevationStandardMax;
                if(!result){
                    initLastModifyHis(repositoryGroupVo);
                }
                return result;
            }else{
                return true;
            }
        }
        initLastModifyHis(repositoryGroupVo);
        return false;
    }

}
