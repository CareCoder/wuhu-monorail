package net.cdsunrise.wm.schedule.util;

import net.cdsunrise.wm.schedule.vo.SchedulePlanImportVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/***
 * @author gechaoqing
 * 工点计划导入
 */
@Component
public class ScheduleWorkPointPlanImportUtils extends SchedulePlanAbstractImportUtils {
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
        return setPlanVals(row);
    }
}
