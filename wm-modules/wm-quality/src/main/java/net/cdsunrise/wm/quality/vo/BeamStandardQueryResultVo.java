package net.cdsunrise.wm.quality.vo;

import lombok.Data;
import net.cdsunrise.wm.quality.entity.BeamPierStandard;
import net.cdsunrise.wm.quality.entity.BeamStandard;
import net.cdsunrise.wm.quality.repostory.BeamPierStandardRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;

/***
 * @author gechaoqing
 * 查询梁测量数据
 */
@Data
public class BeamStandardQueryResultVo {
    private Page<BeamStandard> beamStandardPage;
    /***
     * 授权类型
     *  1 - 修改
     *  2 - 审核
     */
    private int authType;

    public void setBeamStandardList(RepositoryGroupVo repositoryGroupVo,Page<BeamStandard> beamStandardPage) {
        List<BeamStandard> beamStandardList = beamStandardPage.getContent();
        if(beamStandardList!=null&&!beamStandardList.isEmpty()){
            for(BeamStandard beamStandard : beamStandardList){
                String code = beamStandard.getBeamCode();
                beamStandard.getPierStandardList().forEach(pierStandard -> {
                    if(pierStandard.getInverse()==null){
                        BeamPierStandard inverse = repositoryGroupVo.getPierStandardRepository().findBeamPierInverse(pierStandard.getId(),pierStandard.getPierCode(),code.substring(0,1)+"%");
                        if(inverse!=null){
                            BeamPierStandard inverseCp = new BeamPierStandard();
                            BeanUtils.copyProperties(inverse,inverseCp);
                            pierStandard.setInverse(inverseCp);
                        }
                    }
                });
                beamStandard.validate(repositoryGroupVo);
            }
        }
        this.beamStandardPage = beamStandardPage;
    }


    /***
     * //断面宽差
     * 		var widthStandard = 2;
     * 		//断面高差
     * 		var heightStandard = 10;
     * 		//中心偏位
     * 		var centerStandard = 3;
     * 		//高程偏位
     * 		var elevationStandard = [-15,30];
     * 		//梁长、梁高限差
     * 		var beamWidthHeightStandard = 10;
     */
}
