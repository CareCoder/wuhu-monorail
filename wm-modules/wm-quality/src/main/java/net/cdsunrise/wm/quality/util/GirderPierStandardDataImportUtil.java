package net.cdsunrise.wm.quality.util;

import net.cdsunrise.wm.quality.entity.GirderPier;
import net.cdsunrise.wm.quality.entity.PierCoordinateStandard;
import net.cdsunrise.wm.quality.entity.PierDisStandard;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 梁墩线性测量标准数据导入
 */
@Component
public class GirderPierStandardDataImportUtil {
    private final int rowStart = 4;
    private String girderCode = "girderCode";
    private String pierCode = "pierCode";
    private String mileage = "mileage";
    private String data="data";
    private String x="c-x";
    private String y="c-y";
    private String z="c-z";
    private String[] cells=new String[]{
            "lineType",
            girderCode,
            pierCode+"-1",
            "~",
            pierCode+"-2",
            mileage,
            data+"-1-1-f",
            data+"-1-1-h",
            data+"-1-2-f",
            data+"-1-2-h",
            data+"-1-3-f",
            data+"-1-3-h",
            data+"-1-4-f",
            data+"-1-4-h",
            data+"-1-5-f",
            data+"-1-5-h",
            data+"-2-1-f",
            data+"-2-1-h",
            data+"-2-2-f",
            data+"-2-2-h",
            data+"-2-3-f",
            data+"-2-3-h",
            data+"-2-4-f",
            data+"-2-4-h",
            x,
            y,
            z
    };
    private int parseData(String cellTitle,Row row,Cell cell,GirderPier pier,int ci){
        if(cellTitle.startsWith(data)){
            String[] dataItem = cellTitle.split("-");
            int type = Integer.parseInt(dataItem[1]);
            int num = Integer.parseInt(dataItem[2]);
            String fh = dataItem[3];
            //平距
            if("f".equals(fh)){
                PierDisStandard disStandard = new PierDisStandard();
                disStandard.setFlatDis(cell.getNumericCellValue());
                disStandard.setNum(num);
                disStandard.setType(type);
                ci++;
                //高差
                Cell hDiff = row.getCell(ci);
                disStandard.setHeightDiff(hDiff.getNumericCellValue());
                pier.addDisStandard(disStandard);
            }
        }else if(x.equals(cellTitle)){
            PierCoordinateStandard coordinateStandard = new PierCoordinateStandard();
            coordinateStandard.setX(cell.getNumericCellValue());
            ci++;
            Cell cY = row.getCell(ci);
            coordinateStandard.setY(cY.getNumericCellValue());
            ci++;
            Cell cZ = row.getCell(ci);
            coordinateStandard.setZ(cZ.getNumericCellValue());
            pier.setCoordinateStandard(coordinateStandard);
        }
        return ci;
    }
    private GirderPier parseTheNextRow(String girderCode,String pierCode,Row row){
        GirderPier pier = new GirderPier();
        pier.setGirderCode(girderCode);
        pier.setPierCode(pierCode);
        pier.setPierSize(2);
        for(int ci=0;ci<cells.length;ci++){
            String cellTitle = cells[ci];
            Cell cell = row.getCell(ci);
            ci = parseData(cellTitle,row,cell,pier,ci);
        }
        return pier;
    }
    public List<GirderPier> importDataParse(Workbook wb){
        Sheet firstSheet = wb.getSheetAt(0);
        int rowSize = firstSheet.getLastRowNum();
        List<GirderPier> girderPierList = new ArrayList<>();
        for (int j = rowStart; j < rowSize; j++) {
            Row row = firstSheet.getRow(j);
            if (row == null) {
                continue;
            }
            GirderPier pier = new GirderPier();
            girderPierList.add(pier);
            for(int ci=0;ci<cells.length;ci++){
                String cellTitle = cells[ci];
                Cell cell = row.getCell(ci);
                if(girderCode.equals(cellTitle)){
                    pier.setGirderCode(cell.getStringCellValue());
                }else if(cellTitle.startsWith(pierCode)){
                    String pierCode = cell.getStringCellValue();
                    if(StringUtils.isNotBlank(pierCode)){
                        int size = Integer.valueOf(cellTitle.split("-")[1]);
                        //小里程端
                        if(size==1){
                            pier.setPierSize(1);
                            pier.setPierCode(pierCode);
                        }else{
                            j++;
                            //大里程端
                            girderPierList.add(parseTheNextRow(pier.getGirderCode(),pierCode,firstSheet.getRow(j)));
                        }

                    }
                }else {
                    ci = parseData(cellTitle,row,cell,pier,ci);
                }
            }
        }
        return girderPierList;
    }
}
