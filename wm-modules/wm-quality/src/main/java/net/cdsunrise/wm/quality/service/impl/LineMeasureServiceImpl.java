package net.cdsunrise.wm.quality.service.impl;

import net.cdsunrise.wm.base.exception.ServiceErrorException;
import net.cdsunrise.wm.base.util.ExcelImport;
import net.cdsunrise.wm.quality.entity.GirderPier;
import net.cdsunrise.wm.quality.entity.PierCoordinateStandard;
import net.cdsunrise.wm.quality.entity.PierDisStandard;
import net.cdsunrise.wm.quality.repostory.GirderPierRepository;
import net.cdsunrise.wm.quality.repostory.PierCoordinateStandardRepository;
import net.cdsunrise.wm.quality.repostory.PierDisStandardRepository;
import net.cdsunrise.wm.quality.service.LineMeasureService;
import net.cdsunrise.wm.quality.util.GirderPierStandardDataImportUtil;
import net.cdsunrise.wm.quality.vo.PierCoordinateStandardVo;
import net.cdsunrise.wm.quality.vo.PierDisStandardDeviationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/***
 * @author gechaoqing
 */
@Service
public class LineMeasureServiceImpl implements LineMeasureService {

    @Autowired
    private GirderPierStandardDataImportUtil dataImportUtil;
    @Autowired
    private GirderPierRepository repository;
    @Autowired
    private PierDisStandardRepository standardRepository;
    @Autowired
    private PierCoordinateStandardRepository coordinateStandardRepository;
    @Override
    public void standardDataImport(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new ServiceErrorException("无文件可处理");
        }
        List<GirderPier> girderPierList = dataImportUtil.importDataParse(ExcelImport.generateWorkbook(multipartFile));
        repository.save(girderPierList);
    }

    @Override
    public List<GirderPier> getPierByGirderCode(String girderCode) {
        return repository.findByGirderCode(girderCode);
    }

    @Override
    public List<GirderPier> getPierForInput(String girderCode) {
        List<GirderPier> girderPierList = getPierByGirderCode(girderCode);
        for(GirderPier girderPier:girderPierList){
            GirderPier inverse = repository.findByIdNotAndPierCode(girderPier.getId(),girderPier.getPierCode());
            girderPier.setInverse(inverse);
        }
        return girderPierList;
    }

    @Override
    public void saveDeviation(List<PierDisStandardDeviationVo> pierDisStandardList) {
        if(pierDisStandardList==null||pierDisStandardList.isEmpty()){
            return;
        }
        for(PierDisStandardDeviationVo standard:pierDisStandardList){
            standardRepository.update(standard.getFlatDeviation(),standard.getHeightDeviation(),standard.getId());
        }
    }

    @Override
    public void saveCoorDeviation(PierCoordinateStandardVo coordinateStandard) {
        coordinateStandardRepository.updateDeviation(coordinateStandard.getId(),coordinateStandard.getXDeviation(),coordinateStandard.getYDeviation(),coordinateStandard.getZDeviation());
    }
}
