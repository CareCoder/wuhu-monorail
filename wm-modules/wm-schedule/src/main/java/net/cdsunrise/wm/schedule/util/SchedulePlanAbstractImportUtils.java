package net.cdsunrise.wm.schedule.util;

import net.cdsunrise.wm.schedule.enums.SchedulePlanCategory;
import net.cdsunrise.wm.schedule.vo.SchedulePlanImportVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author gechaoqing
 * 进度导入
 */
public abstract class SchedulePlanAbstractImportUtils {
    protected String order = "order";
    protected String name = "name";
    protected String startDate = "startDate";
    protected String completeDate = "completeDate";
    protected String days = "days";
    protected String unit = "unit";
    protected String quantity = "quantity";
    /***
     * 列名称与位置信息
     * @return
     */
    protected abstract String[] cells();

    /***
     * 开始读取的行
     * @return
     */
    protected abstract int rowStart();

    /***
     * 遍历行
     * @param row
     * @return
     */
    protected abstract SchedulePlanImportVo eachRow(Row row);
    int getTheTitleIndex (String theTitle){
        String[] cells = cells();
        for (int j = 0; j < cells.length; j++) {
            String title = cells[j];
            if(title.equals(theTitle)){
                return j;
            }
        }
        return -1;
    }

    private Map<String,SchedulePlanImportVo> voMap;

    void setOrderMapVo(String order,SchedulePlanImportVo vo){
        voMap.put(order,vo);
    }
    SchedulePlanImportVo getVo(String order){
        return voMap.get(order);
    }
    protected SchedulePlanImportVo setPlanVals(Row row){
        SchedulePlanImportVo plan = new SchedulePlanImportVo();
        Cell cell = row.getCell(getTheTitleIndex(name));
        plan.setName(cell.getStringCellValue());

        cell = row.getCell(getTheTitleIndex(days));
        if(cell!=null){
            if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                plan.setDays((int)cell.getNumericCellValue());
            }else{
                String cellVal = cell.toString();
                if(StringUtils.isNumeric(cellVal)){
                    plan.setDays(Integer.valueOf(cellVal));
                }
            }
        }
        cell = row.getCell(getTheTitleIndex(unit));
        if(cell!=null){
            plan.setUnit(cell.getStringCellValue());
        }

        cell = row.getCell(getTheTitleIndex(quantity));
        if(cell!=null){
            if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                plan.setQuantity(new BigDecimal(cell.getNumericCellValue()));
            }else{
                String cellVal = cell.toString();
                if(StringUtils.isNotBlank(cellVal)){
                    plan.setQuantity(new BigDecimal(cellVal));
                }
            }
        }
        cell = row.getCell(getTheTitleIndex(startDate));
        if(cell!=null&&cell.getCellType()!=Cell.CELL_TYPE_BLANK){
            if(cell.getCellType()!=Cell.CELL_TYPE_STRING){
                plan.setStartDate(cell.getDateCellValue());
            }

        }
        cell = row.getCell(getTheTitleIndex(completeDate));
        if(cell!=null&&cell.getCellType()!=Cell.CELL_TYPE_BLANK)
        {
            if(cell.getCellType()!=Cell.CELL_TYPE_STRING) {
                plan.setCompleteDate(cell.getDateCellValue());
            }
        }
        return plan;
    }
    public List<SchedulePlanImportVo> parseExcel(Workbook wb, SchedulePlanCategory planCategory) {
        voMap = new HashMap<>(20);
        Sheet firstSheet = wb.getSheetAt(0);
        List<SchedulePlanImportVo> planList = new ArrayList<>();
        int rowSize = firstSheet.getLastRowNum() + 1;
        for (int j = rowStart(); j < rowSize; j++) {
            Row row = firstSheet.getRow(j);
            if (row == null) {
                continue;
            }
            SchedulePlanImportVo plan = eachRow(row);
            if(plan!=null){
                plan.setCategory(planCategory.name());
                planList.add(plan);
            }
        }
        return planList;
    }
}
