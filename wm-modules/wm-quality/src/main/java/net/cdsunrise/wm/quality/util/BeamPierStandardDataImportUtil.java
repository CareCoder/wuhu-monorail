package net.cdsunrise.wm.quality.util;

import net.cdsunrise.wm.quality.entity.*;
import net.cdsunrise.wm.quality.repostory.BeamStandardRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 梁墩线性测量标准数据导入第二版
 */
@Component
public class BeamPierStandardDataImportUtil {
    @Autowired
    private BeamStandardRepository standardRepository;
    private final int rowStart = 4;

    private final int[][] pierCellIndex={{2,5,6,7,8,9,10,11,12},{4,13,14,15,16,17,18,19,20}};

    /**
     * 断面列索引
     */
    private final int[][] sectionCellIndex={{23,24,25,26},{27,28,29,30},{31,32,33,34}};
    private final String[] sectionTypes={"1/4","2/4","3/4"};
    /***
     * 解析每行标准数据
     */
    private void parseData(Row row,BeamStandard beamStandard){
        //梁号
        Cell cell = row.getCell(1);
        if(cell==null){
            return;
        }
        beamStandard.setBeamCode(cell.getStringCellValue());

        BeamStandard temp = standardRepository.findOne(beamStandard.getBeamCode());
        if(temp!=null){
            standardRepository.delete(temp);
        }

        //梁长
        cell = row.getCell(21);
        beamStandard.setLength(getCellBigDecimal(cell));
        //梁高
        cell = row.getCell(22);
        beamStandard.setHeight(getCellBigDecimal(cell));

        //小里程端
        setPierData(row,0,beamStandard);
        //大里程端
        setPierData(row,1,beamStandard);

        //断面
        setSectionData(row,beamStandard);
    }

    /***
     * 断面数据
     */
    private void setSectionData(Row row,BeamStandard beamStandard){
        int current=0;
        for(int[] cellIndex:sectionCellIndex){
            BeamSectionStandard sectionStandard = new BeamSectionStandard();
            sectionStandard.setSectionType(sectionTypes[current]);
            //顶面宽度
            Cell cell = row.getCell(cellIndex[0]);
            sectionStandard.setTopWidth(getCellBigDecimal(cell));

            //底面宽度
            cell = row.getCell(cellIndex[1]);
            sectionStandard.setBottomWidth(getCellBigDecimal(cell));

            //左侧高度
            cell = row.getCell(cellIndex[2]);
            sectionStandard.setLeftHeight(getCellBigDecimal(cell));

            //右侧高度
            cell = row.getCell(cellIndex[3]);
            sectionStandard.setRightHeight(getCellBigDecimal(cell));

            beamStandard.addSectionStandard(sectionStandard);
            current++;
        }
    }

    private BigDecimal getCellBigDecimal(Cell cell){
        if(cell==null){
            return null;
        }
        if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
            return new BigDecimal(cell.getNumericCellValue());
        }else if(cell.getCellType()==Cell.CELL_TYPE_STRING){
            String cellVal = cell.getStringCellValue();
            if(isNumber(cellVal)){
                return new BigDecimal(cellVal);
            }
        }
        return null;
    }

    private boolean isNumber(String str){
        if(str==null){
            return false;
        }
        return str.matches("^[0-9]+(.[0-9]+)?$");
    }

    /***
     * 墩标准数据
     */
    private void setPierData(Row row,int num,BeamStandard beamStandard){
        int[] cellIndex = pierCellIndex[num];
        //墩 - 小里程端
        Cell cell = row.getCell(cellIndex[0]);
        BeamPierStandard pierStandard = new BeamPierStandard();
        pierStandard.setPierSize(num+1);
        pierStandard.setPierCode(cell.getStringCellValue());
        // 顶面宽度
        cell = row.getCell(cellIndex[1]);
        pierStandard.setTopWidth(getCellBigDecimal(cell));

        // 地面宽度
        cell = row.getCell(cellIndex[2]);
        pierStandard.setBottomWidth(getCellBigDecimal(cell));

        // 左侧高度
        cell = row.getCell(cellIndex[3]);
        pierStandard.setLeftHeight(getCellBigDecimal(cell));

        //右侧高度
        cell = row.getCell(cellIndex[4]);
        pierStandard.setRightHeight(getCellBigDecimal(cell));

        setSupportData(row,cellIndex,pierStandard);

        beamStandard.addPierStandard(pierStandard);
    }

    /***
     * 临时支撑数据
     */
    private void setSupportData(Row row,int[] cellIndex,BeamPierStandard pierStandard){
        // 梁端临时支撑
        BeamPierSupportStandard supportStandard = new BeamPierSupportStandard();
        supportStandard.setSupportType(BeamPierSupportStandard.SUPPORT_TYPE_BEAM);
        //中心偏位
        Cell cell = row.getCell(cellIndex[5]);
        supportStandard.setCenterDeviation(getCellBigDecimal(cell));

        //高程偏位
        cell = row.getCell(cellIndex[6]);
        supportStandard.setHeightDeviation(getCellBigDecimal(cell));

        pierStandard.addSupportStandard(supportStandard);

        //墩身临时支撑
        supportStandard = new BeamPierSupportStandard();
        supportStandard.setSupportType(BeamPierSupportStandard.SUPPORT_TYPE_PIER);
        //中心偏位
        cell = row.getCell(cellIndex[7]);
        supportStandard.setCenterDeviation(getCellBigDecimal(cell));

        //高程偏位
        cell = row.getCell(cellIndex[8]);
        supportStandard.setHeightDeviation(getCellBigDecimal(cell));

        pierStandard.addSupportStandard(supportStandard);
    }
    public List<BeamStandard> importDataParse(Workbook wb){
        Sheet firstSheet = wb.getSheetAt(0);
        int rowSize = firstSheet.getLastRowNum();
        List<BeamStandard> beamStandardList = new ArrayList<>();
        for (int j = rowStart; j <= rowSize; j++) {
            Row row = firstSheet.getRow(j);
            if (row == null) {
                continue;
            }
            BeamStandard beamStandard = new BeamStandard();
            parseData(row,beamStandard);
            if(beamStandard.getBeamCode()!=null)
            {
                beamStandardList.add(beamStandard);
            }
        }
        return beamStandardList;
    }
}
