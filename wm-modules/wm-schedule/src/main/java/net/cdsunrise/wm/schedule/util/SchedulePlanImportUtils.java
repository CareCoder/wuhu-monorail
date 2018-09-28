package net.cdsunrise.wm.schedule.util;

import net.cdsunrise.wm.schedule.vo.SchedulePlanImportVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

/***
 * @author gechaoqing
 * 年计划导入
 */
@Component
public class SchedulePlanImportUtils extends SchedulePlanAbstractImportUtils {
    private String[] cells = new String[]{
            order,
            name,
            unit,
            quantity,
            startDate,
            completeDate,
            days,
            "target",
            "affect",
            "remark"
    };
    private String zero=".0";
    private String point = ".";

    private String skipOrder="一二三四五六七八九十";

    @Override
    protected int rowStart() {
        return 2;
    }

    @Override
    protected String[] cells() {
        return cells;
    }

    @Override
    public SchedulePlanImportVo eachRow(Row row) {
        Cell cell = row.getCell(getTheTitleIndex(order));
        String order = cell.toString();
        if(skipOrder.contains(order)){
            return null;
        }
        SchedulePlanImportVo plan = setPlanVals(row);
        if(order.contains(zero)){
            order = order.replace(zero,"");
        }

        setOrderMapVo(order,plan);
        if(!StringUtils.isNumeric(order)){
            int parentIndex = order.lastIndexOf(point);
            if(parentIndex!=-1){
                String parentOrder = order.substring(0,parentIndex);
                SchedulePlanImportVo parent = getVo(parentOrder);
                if(parent!=null){
                    if(parentOrder.contains(point)){
                        plan.setName("("+parent.getName()+")"+plan.getName());
                    }
                    parent.getChildren().add(plan);
                    //如果是子节点，则已经放到父节点的children列表里了，无需再返回
                    return null;
                }
            }
        }
        return plan;
    }
}
