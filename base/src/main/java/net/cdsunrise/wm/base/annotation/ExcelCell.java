package net.cdsunrise.wm.base.annotation;

import java.lang.annotation.*;

/**
 * Author : WangRui
 * Date : 2018/4/16
 * Describe :
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelCell {
    int index() default 0;//列标记
    ExcelCellType value();//Cell类型
}
