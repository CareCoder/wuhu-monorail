package net.cdsunrise.wm.quality.service.impl;

import net.cdsunrise.wm.base.exception.ServiceErrorException;
import net.cdsunrise.wm.base.util.ExcelImport;
import net.cdsunrise.wm.quality.entity.*;
import net.cdsunrise.wm.quality.enums.Status;
import net.cdsunrise.wm.quality.feign.SystemFeign;
import net.cdsunrise.wm.quality.repostory.*;
import net.cdsunrise.wm.quality.service.BeamStandardService;
import net.cdsunrise.wm.quality.util.BeamPierStandardDataImportUtil;
import net.cdsunrise.wm.quality.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/***
 * @author gechaoqing
 * 梁墩标准数据服务
 */
@Service
public class BeamStandardServiceImpl implements BeamStandardService {
    @Autowired
    private BeamStandardRepository standardRepository;
    @Autowired
    private BeamPierStandardDataImportUtil standardDataImportUtil;
    @Autowired
    private BeamPierStandardRepository pierStandardRepository;
    @Autowired
    private BeamSectionStandardRepository sectionStandardRepository;
    @Autowired
    private BeamSectionStandardModifyHisRepository sectionStandardModifyHisRepository;

    @Autowired
    private BeamPierSupportStandardRepository supportStandardRepository;

    @Autowired
    private BeamStandardModifyHisRepository standardModifyHisRepository;
    @Autowired
    private BeamPierSupportStandardModifyHisRepository supportStandardModifyHisRepository;
    @Autowired
    private SystemFeign systemFeign;

    @Override
    public void beamStandardImport(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new ServiceErrorException("无文件可处理");
        }
        List<BeamStandard> standardList = standardDataImportUtil.importDataParse(ExcelImport.generateWorkbook(multipartFile));
        standardRepository.save(standardList);
    }

    @Override
    public BeamStandard getByBeamCode(String code) {
        BeamStandard standard = standardRepository.findOne(code);
        List<BeamPierStandard> pierStandardList = standard.getPierStandardList();
        for(BeamPierStandard pierStandard:pierStandardList){
            BeamPierStandard inverse = pierStandardRepository.findBeamPierInverse(pierStandard.getId(),pierStandard.getPierCode(),code.substring(0,1)+"%");
            pierStandard.setInverse(inverse);
        }
        return standard;
    }

    private Status checkStatus(String status){
        try{
            Status status1 = Status.valueOf(status);
            if(status1==Status.NONE){
                throw new ServiceErrorException("[NONE]状态不可用！");
            }
            return status1;
        }catch (Exception e){
            throw new ServiceErrorException("状态不正确！");
        }
    }
    @Override
    public void auditBeamDeviations(String beamCode,String status){
        BeamStandardModifyHis modifyHis = standardModifyHisRepository.findForModifyCheck(beamCode);
        if(modifyHis==null){
            throw new ServiceErrorException("没有找到可以审核的数据");
        }
        BeamStandard beamStandard = standardRepository.findOne(beamCode);
        if(beamStandard==null){
            throw new ServiceErrorException("梁标准数据不存在！");
        }
        Status status1 = checkStatus(status);
        if(Status.OK==status1){
            if(modifyHis.getHeightDeviation()!=null){
                beamStandard.setHeightDeviation(modifyHis.getHeightDeviation());
            }
            if(modifyHis.getLengthDeviation()!=null){
                beamStandard.setLengthDeviation(modifyHis.getLengthDeviation());
            }
            standardRepository.save(beamStandard);
        }
        modifyHis.setStatus(status);
        standardModifyHisRepository.save(modifyHis);
    }

    @Override
    public void saveBeamDeviations(BeamDeviationsVo beamDeviationsVo) {
        if(StringUtils.isBlank(beamDeviationsVo.getBeamCode())){
            throw new ServiceErrorException("没有梁号参数，保存失败！");
        }
        BeamStandard beamStandard = standardRepository.findOne(beamDeviationsVo.getBeamCode());
        if(beamStandard!=null){
            UserVo userVo = systemFeign.fetchCurrentUser();
            if(!beamDeviationsVo.isModify()){
                beamStandard.setMeasureUserId(userVo.getId());
                beamStandard.setMeasureUserName(userVo.getRealName());
                beamStandard.setLengthDeviation(beamDeviationsVo.getLengthDeviation());
                beamStandard.setHeightDeviation(beamDeviationsVo.getHeightDeviation());
                beamStandard.setModifyTime(new Date());
                standardRepository.save(beamStandard);
            }else{
                BeamStandardModifyHis modifyHis = standardModifyHisRepository.findForModifyCheck(beamStandard.getBeamCode());
                if(modifyHis==null){
                    modifyHis = new BeamStandardModifyHis();
                    modifyHis.setBeamCode(beamStandard.getBeamCode());
                }
                modifyHis.setModifyUserId(userVo.getId());
                modifyHis.setModifyUserName(userVo.getRealName());
                if(beamDeviationsVo.getLengthDeviation()!=null){
                    modifyHis.setLengthDeviation(beamDeviationsVo.getLengthDeviation());
                }
                if(beamDeviationsVo.getHeightDeviation()!=null){
                    modifyHis.setHeightDeviation(beamDeviationsVo.getHeightDeviation());
                }
                modifyHis.setStatus(Status.NONE.name());
                standardModifyHisRepository.save(modifyHis);
            }
        }
        //standardRepository.updateBeamDeviations(beamDeviationsVo);
    }

    @Override
    public void auditPierSupportDeviations(Long id,String status){
        BeamPierSupportStandardModifyHis modifyHis = supportStandardModifyHisRepository.findForModifyCheck(id);
        if(modifyHis==null){
            throw new ServiceErrorException("没有找到可以审核的临时支撑数据");
        }
        BeamPierSupportStandard supportStandard = supportStandardRepository.findOne(id);
        if(supportStandard==null){
            throw new ServiceErrorException("临时支撑标准数据不存在！");
        }
        Status status1 = checkStatus(status);
        if(Status.OK==status1){
            if(modifyHis.getCenterDeviationVal()!=null){
                supportStandard.setCenterDeviationVal(modifyHis.getCenterDeviationVal());
            }
            if(modifyHis.getHeightDeviationVal()!=null){
                supportStandard.setHeightDeviationVal(modifyHis.getHeightDeviationVal());
            }
            supportStandardRepository.save(supportStandard);
        }
        modifyHis.setStatus(status);
        supportStandardModifyHisRepository.save(modifyHis);
    }

    @Override
    public void savePierSupportDeviations(BeamPierSupportDeviationsVo deviationsVo) {
        if(deviationsVo.getId()==null){
             throw new ServiceErrorException("没有唯一标识，保存失败！");
        }
        BeamPierSupportStandard supportStandard = supportStandardRepository.findOne(deviationsVo.getId());
        if(supportStandard!=null){
            UserVo userVo = systemFeign.fetchCurrentUser();
            if(!deviationsVo.isModify()){
                supportStandard.setMeasureUserId(userVo.getId());
                supportStandard.setMeasureUserName(userVo.getRealName());
                supportStandard.setCenterDeviationVal(deviationsVo.getCenterDeviationVal());
                supportStandard.setHeightDeviationVal(deviationsVo.getHeightDeviationVal());
                supportStandard.getPierStandard().getBeamStandard().setModifyTime(new Date());
                supportStandardRepository.save(supportStandard);
            }else{
                BeamPierSupportStandardModifyHis modifyHis = supportStandardModifyHisRepository.findForModifyCheck(supportStandard.getId());
                if(modifyHis==null){
                    modifyHis = new BeamPierSupportStandardModifyHis();
                    modifyHis.setId(supportStandard.getId());
                }
                modifyHis.setModifyUserId(userVo.getId());
                modifyHis.setModifyUserName(userVo.getRealName());
                if(deviationsVo.getCenterDeviationVal()!=null){
                    modifyHis.setCenterDeviationVal(deviationsVo.getCenterDeviationVal());
                }
                if(deviationsVo.getHeightDeviationVal()!=null){
                    modifyHis.setHeightDeviationVal(deviationsVo.getHeightDeviationVal());
                }
                modifyHis.setStatus(Status.NONE.name());
                supportStandardModifyHisRepository.save(modifyHis);
            }
        }
    }

    @Override
    public void auditSectionDeviations(Integer intType,Long id,String status){
        String type =intType==1?"PIER":"BEAM";
        Status status1 = checkStatus(status);

        BeamSectionStandardModifyHis modifyHis = sectionStandardModifyHisRepository.findForModifyCheck(id,type);
        if(modifyHis==null){
            throw new ServiceErrorException("没有找到可以审核的断面数据");
        }
        if(intType==1){
            BeamPierStandard pierStandard = pierStandardRepository.findOne(id);
            if(pierStandard==null){
                throw new ServiceErrorException("断面数据不存在！");
            }
            if(status1==Status.OK){
                if(modifyHis.getBottomWidthDeviation()!=null){
                    pierStandard.setBottomWidthDeviation(modifyHis.getBottomWidthDeviation());
                }
                if(modifyHis.getTopWidthDeviation()!=null){
                    pierStandard.setTopWidthDeviation(modifyHis.getTopWidthDeviation());
                }
                if(modifyHis.getLeftHeightDeviation()!=null){
                    pierStandard.setLeftHeightDeviation(modifyHis.getLeftHeightDeviation());
                }
                if(modifyHis.getRightHeightDeviation()!=null){
                    pierStandard.setRightHeightDeviation(modifyHis.getRightHeightDeviation());
                }
                pierStandardRepository.save(pierStandard);
            }
        }else if(intType==2){
            BeamSectionStandard sectionStandard = sectionStandardRepository.findOne(id);
            if(sectionStandard==null){
                throw new ServiceErrorException("断面数据不存在!");
            }
            if(status1==Status.OK){
                if(modifyHis.getBottomWidthDeviation()!=null){
                    sectionStandard.setBottomWidthDeviation(modifyHis.getBottomWidthDeviation());
                }
                if(modifyHis.getTopWidthDeviation()!=null){
                    sectionStandard.setTopWidthDeviation(modifyHis.getTopWidthDeviation());
                }
                if(modifyHis.getLeftHeightDeviation()!=null){
                    sectionStandard.setLeftHeightDeviation(modifyHis.getLeftHeightDeviation());
                }
                if(modifyHis.getRightHeightDeviation()!=null){
                    sectionStandard.setRightHeightDeviation(modifyHis.getRightHeightDeviation());
                }
                sectionStandardRepository.save(sectionStandard);
            }
        }else {
            throw new ServiceErrorException("不支持的操作！");
        }
        modifyHis.setStatus(status);
        sectionStandardModifyHisRepository.save(modifyHis);
    }

    private void saveSectionModifyHis(BeamSectionDeviationsVo deviationsVo,UserVo userVo){
        String type = deviationsVo.getType()==1?"PIER":"BEAM";
        BeamSectionStandardModifyHis modifyHis = sectionStandardModifyHisRepository.findForModifyCheck(deviationsVo.getId(),type);
        if(modifyHis==null){
            modifyHis = new BeamSectionStandardModifyHis();
            modifyHis.setType(type);
            modifyHis.setId(deviationsVo.getId());
        }
        modifyHis.setStatus(Status.NONE.name());
        modifyHis.setModifyUserId(userVo.getId());
        modifyHis.setModifyUserName(userVo.getRealName());
        if(deviationsVo.getBottomWidthDeviation()!=null)
        {
            modifyHis.setBottomWidthDeviation(deviationsVo.getBottomWidthDeviation());
        }
        if(deviationsVo.getTopWidthDeviation()!=null){
            modifyHis.setTopWidthDeviation(deviationsVo.getTopWidthDeviation());
        }
        if(deviationsVo.getLeftHeightDeviation()!=null){
            modifyHis.setLeftHeightDeviation(deviationsVo.getLeftHeightDeviation());
        }
        if(deviationsVo.getRightHeightDeviation()!=null){
            modifyHis.setRightHeightDeviation(deviationsVo.getRightHeightDeviation());
        }

        sectionStandardModifyHisRepository.save(modifyHis);
    }
    @Override
    public void saveSectionDeviations(BeamSectionDeviationsVo deviationsVo) {
        if(deviationsVo.getId()==null){
            throw new ServiceErrorException("没有唯一标识，保存失败！");
        }
        UserVo userVo = systemFeign.fetchCurrentUser();
        if(deviationsVo.getType()==1){
            BeamPierStandard pierStandard = pierStandardRepository.findOne(deviationsVo.getId());
            if(pierStandard!=null){
                if(!deviationsVo.isModify()){
                    pierStandard.setMeasureUserId(userVo.getId());
                    pierStandard.setMeasureUserName(userVo.getRealName());
                    pierStandard.setTopWidthDeviation(deviationsVo.getTopWidthDeviation());
                    pierStandard.setBottomWidthDeviation(deviationsVo.getBottomWidthDeviation());
                    pierStandard.setLeftHeightDeviation(deviationsVo.getLeftHeightDeviation());
                    pierStandard.setRightHeightDeviation(deviationsVo.getRightHeightDeviation());
                    pierStandard.getBeamStandard().setModifyTime(new Date());
                    pierStandardRepository.save(pierStandard);
                }else{
                    saveSectionModifyHis(deviationsVo,userVo);
                }

            }
        }else if(deviationsVo.getType()==2){
            BeamSectionStandard sectionStandard = sectionStandardRepository.findOne(deviationsVo.getId());
            if(sectionStandard!=null){
                if(!deviationsVo.isModify()){
                    sectionStandard.setMeasureUserId(userVo.getId());
                    sectionStandard.setMeasureUserName(userVo.getRealName());
                    sectionStandard.setTopWidthDeviation(deviationsVo.getTopWidthDeviation());
                    sectionStandard.setBottomWidthDeviation(deviationsVo.getBottomWidthDeviation());
                    sectionStandard.setLeftHeightDeviation(deviationsVo.getLeftHeightDeviation());
                    sectionStandard.setRightHeightDeviation(deviationsVo.getRightHeightDeviation());
                    sectionStandard.getBeamStandard().setModifyTime(new Date());
                    sectionStandardRepository.save(sectionStandard);
                }else{
                    saveSectionModifyHis(deviationsVo,userVo);
                }
            }
        }else{
            throw new ServiceErrorException("未知操作！");
        }
    }

    @Override
    public BeamStandardQueryResultVo allBeamStandard(int currentPage,int pageSize) {
        PageRequest request = new PageRequest(currentPage-1,pageSize);
        BeamStandardQueryResultVo queryResultVo = new BeamStandardQueryResultVo();
        RepositoryGroupVo repositoryGroupVo = new RepositoryGroupVo(pierStandardRepository,sectionStandardModifyHisRepository,standardModifyHisRepository,supportStandardModifyHisRepository);
        queryResultVo.setBeamStandardList(repositoryGroupVo,standardRepository.findByModifyTimeIsNotNull(request));
        Long deptId = systemFeign.fetchCurrentUserDeptId();
        if(deptId==1){
            queryResultVo.setAuthType(2);
        }else{
            queryResultVo.setAuthType(1);
        }

        return queryResultVo;
    }
}
