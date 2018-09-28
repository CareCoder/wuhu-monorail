package net.cdsunrise.wm.quality.vo;

import lombok.Data;
import net.cdsunrise.wm.quality.repostory.BeamPierStandardRepository;
import net.cdsunrise.wm.quality.repostory.BeamPierSupportStandardModifyHisRepository;
import net.cdsunrise.wm.quality.repostory.BeamSectionStandardModifyHisRepository;
import net.cdsunrise.wm.quality.repostory.BeamStandardModifyHisRepository;

/***
 * @author gechaoqing
 * 仓库组
 */
@Data
public class RepositoryGroupVo {
    private BeamPierStandardRepository pierStandardRepository;
    private BeamSectionStandardModifyHisRepository sectionStandardModifyHisRepository;
    private BeamStandardModifyHisRepository standardModifyHisRepository;
    private BeamPierSupportStandardModifyHisRepository supportStandardModifyHisRepository;

    public RepositoryGroupVo(BeamPierStandardRepository pierStandardRepository
            , BeamSectionStandardModifyHisRepository sectionStandardModifyHisRepository
            , BeamStandardModifyHisRepository standardModifyHisRepository
            , BeamPierSupportStandardModifyHisRepository supportStandardModifyHisRepository) {
        this.pierStandardRepository = pierStandardRepository;
        this.sectionStandardModifyHisRepository = sectionStandardModifyHisRepository;
        this.standardModifyHisRepository = standardModifyHisRepository;
        this.supportStandardModifyHisRepository = supportStandardModifyHisRepository;
    }
}
