package net.cdsunrise.wm.beamfield.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.util.ExcelExport;
import net.cdsunrise.wm.base.util.ExcelImport;
import net.cdsunrise.wm.base.web.annotation.*;
import net.cdsunrise.wm.beamfield.entity.Ledger;
import net.cdsunrise.wm.beamfield.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author lijun
 * @date 2018-04-02.
 */
@Api(value = "台账管理")
@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    /**
     * 台账Excel导出模板绝对路径
     */
    @Value("${tbExcelModelPath}")
    private String ledgerExcelModelPath;

    @ApiOperation(value = "新增/更新")
    @SavePath
    public void save(Ledger ledger) {
        ledgerService.save(ledger);
    }

    @ApiOperation(value = "删除")
    @DeletePath
    public void delete(Long id) {
        ledgerService.delete(id);
    }

    @ApiOperation(value = "查询单个信息")
    @GetPath
    public Ledger get(Long id) {
        Ledger ledger = ledgerService.findOne(id);
        return ledger;
    }

    @ApiOperation(value = "分页查询/台账预览")
    @PagePath
    public Pager<Ledger> getPager(PageCondition condition) {
        Pager<Ledger> pager = ledgerService.getPager(condition);
        return pager;
    }

    @ApiOperation(value = "Excel导入")
    @PostMapping(value = "/ledgerImport")
    public String ledgerImport(MultipartFile file) throws ParseException {
        try {
            List<Ledger> ledgers = ExcelImport.excelTransformationEntityList(Ledger.class, file.getInputStream(), file.getOriginalFilename(), 5, 1);
            ledgerService.saveBatch(ledgers);
            return "导入成功。";
        } catch (IOException e) {
            e.printStackTrace();
            return "导入失败,请联系系统管理员。";
        }
    }

    @ApiOperation(value = "Excel导出")
    @GetMapping(value = "/ledgerExport")
    public void ledgerExport(HttpServletResponse response) throws IOException {
        List<Ledger> ledgers = ledgerService.findAll();
        Ledger ledger;
        if (ledgers.size() > 0) {
            List<Map<Integer, Object>> list = new ArrayList<>();
            for (int i = 0; i < ledgers.size(); i++) {
                ledger = ledgers.get(i);
                Map<Integer, Object> entityMap = new HashMap<>();
                entityMap.put(0, i + 1);
                entityMap.put(1, ledger.getBeamNumber());
                entityMap.put(2, ledger.getGraphNumber());
                entityMap.put(3, ledger.getLine());
                entityMap.put(4, ledger.getScopeOfApplication());
                entityMap.put(5, ledger.getLineInterval());
                entityMap.put(6, ledger.getPierNumberSmallMileage());
                entityMap.put(7, ledger.getPierNumberBigMileage());
                entityMap.put(8, ledger.getMileageNumberS());
                entityMap.put(9, ledger.getMileageNumberB());
                entityMap.put(10, ledger.getBeamType());
                entityMap.put(11, ledger.getBeamSpan());
                entityMap.put(12, ledger.getBeamLength());
                entityMap.put(13, ledger.getBeamRadiusOfCurve());
                entityMap.put(14, ledger.getIsGrant());
                entityMap.put(15, ledger.getSHSmallMileage());
                entityMap.put(16, ledger.getSHBigMileage());
                entityMap.put(17, ledger.getSHLength());
                entityMap.put(18, ledger.getEdPartsEscapeRoute());
                entityMap.put(19, ledger.getEdPartsContactRailChannel());
                entityMap.put(20, ledger.getEdPartsBeacon());
                entityMap.put(21, ledger.getEdPartsSupportingSteel());
                entityMap.put(22, ledger.getEdPartsSupportCSPF());
                entityMap.put(23, ledger.getEdPartsSupportSS());
                entityMap.put(24, ledger.getEdPartsInternalModel());
                entityMap.put(25, ledger.getEdPartsPVC());
                entityMap.put(26, ledger.getEdPartsSteelPipe());
                entityMap.put(27, ledger.getEdPartsFPS());
                entityMap.put(28, ledger.getEdPartsSteelStrand());
                entityMap.put(29, ledger.getEdPartsCP50());
                entityMap.put(30, ledger.getEdPartsCP55());
                entityMap.put(31, ledger.getEdPartsCP60());
                entityMap.put(32, ledger.getEdPartsCP70());
                entityMap.put(33, ledger.getEdPartsAAP3());
                entityMap.put(34, ledger.getEdPartsAAP4());
                entityMap.put(35, ledger.getEdPartsAAP5());
                entityMap.put(36, ledger.getEdPartsAAP6());
                entityMap.put(37, ledger.getEdPartsAAP7());
                entityMap.put(38, ledger.getEdPartsGroutingMaterial());
                entityMap.put(39, ledger.getReinEngDesignHPB8());
                entityMap.put(40, ledger.getReinEngDesignHPB10());
                entityMap.put(41, ledger.getReinEngDesignHRB12());
                entityMap.put(42, ledger.getReinEngDesignHRB16());
                entityMap.put(43, ledger.getReinEngDesignHRB28());
                entityMap.put(44, ledger.getReinEngDesignHRB32());
                entityMap.put(45, ledger.getReinEngSleeve32());
                entityMap.put(46, ledger.getReinEngSleeve16());
                entityMap.put(47, ledger.getReinEngBeamPedestal());
                entityMap.put(48, ledger.getReinEngCplBindTime());
                entityMap.put(49, ledger.getReinEngMoldTime());
                entityMap.put(50, ledger.getPouringTimeB());
                entityMap.put(51, ledger.getPouringTimeE());
                entityMap.put(52, ledger.getSlump());
                entityMap.put(53, ledger.getEntryModelTemperature());
                entityMap.put(54, ledger.getDesignTemperature());
                entityMap.put(55, ledger.getActualTemperature());
                entityMap.put(56, ledger.getDismantleModelTime());
                entityMap.put(57, ledger.getPresTensGroutBeginTensionTime());
                entityMap.put(58, ledger.getPresTensGroutBeamShift());
                entityMap.put(59, ledger.getPresTensGroutBeamStorageNumber());
                entityMap.put(60, ledger.getPresTensGroutEndTensionTime());
                entityMap.put(61, ledger.getPresTensGroutPulping());
                entityMap.put(62, ledger.getIsTransport());
                entityMap.put(63, ledger.getNote());
                list.add(entityMap);
            }
            String fileName = "PC轨道梁台帐-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            ExcelExport.exportByModel(response, fileName, list, ledgerExcelModelPath, 5);
        }
    }

    @ApiOperation("存梁区台账列表")
    @GetMapping("/sbaLedgerList")
    public List<Ledger> sbaLedgerList(){
        return ledgerService.sbaLedgerList();
    }

    @ApiOperation("存梁区台账存梁台座号数组")
    @GetMapping("/sbaLedgerSArray")
    public Map<String,List<String>> sbaLedgerStorgeNumberArray(){
        Map<String,List<String>> map = new HashMap<>();
        map.put("number",ledgerService.sbaLedgerStorgeNumberArray());
        return map;
    }
}
