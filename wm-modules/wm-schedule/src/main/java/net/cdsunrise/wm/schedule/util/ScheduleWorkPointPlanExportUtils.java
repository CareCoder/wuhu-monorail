package net.cdsunrise.wm.schedule.util;

import net.cdsunrise.wm.schedule.entity.SchedulePlan;
import net.cdsunrise.wm.schedule.enums.SchedulePlanCategory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/***
 * @author gechaoqing
 * 工点计划导出
 */
@Component
public class ScheduleWorkPointPlanExportUtils {
    private String[] cells = new String[]{
            "序号","项目/部位",
            "单位","计划工程数量",
            "开始时间","结束时间",
            "日历天","进度指标"
    };
    private int setTableTitles(XSSFSheet sheet,int currentRow){
        Row titleRow = sheet.createRow(currentRow);
        int cellIndex=0;
        for(String cellVal:cells){
            titleRow.createCell(cellIndex).setCellValue(cellVal);
            cellIndex++;
        }
        currentRow++;
        return currentRow;
    }

    public void export(List<SchedulePlan> planList, String workPointName, SchedulePlanCategory planCategory, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(planCategory.getDisc());
        int currentRow = 0;
        Row row = sheet.createRow(currentRow);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,cells.length-1));
        XSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        titleCellStyle.setFont(font);
        Cell titleCell = row.createCell(0);
        titleCell.setCellStyle(titleCellStyle);
        String name = "芜湖市轨道交通项目"+workPointName+planCategory.getDisc();
        titleCell.setCellValue(name);
        currentRow++;
        currentRow = setTableTitles(sheet,currentRow);
        for(SchedulePlan plan:planList){
            Row dataRow = sheet.createRow(currentRow);
            //序号
            Cell cell = dataRow.createCell(0);
            cell.setCellValue(currentRow-1);

            //计划名称 项目部位
            cell = dataRow.createCell(1);
            cell.setCellValue(plan.getName());

            //工程量单位
            cell = dataRow.createCell(2);
            cell.setCellValue(plan.getQuantityUnit());

            //计划工程量
            cell = dataRow.createCell(3);
            if(plan.getQuantity()!=null){
                cell.setCellValue(plan.getQuantity().doubleValue());
            }

            //开始时间
            cell = dataRow.createCell(4);
            cell.setCellValue(plan.getStartDate());

            //结束时间
            cell = dataRow.createCell(5);
            cell.setCellValue(plan.getCompleteDate());

            //日历天
            cell = dataRow.createCell(6);
            cell.setCellValue(plan.getTimeLimit());

            //进度指标
            cell = dataRow.createCell(7);
            cell.setCellValue("");

            currentRow++;
        }
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8") + ".xlsx");
        response.setContentType("application/ms-excel;charset=UTF-8");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        outputStream.flush();
    }

}
