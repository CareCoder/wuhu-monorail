package net.cdsunrise.wm.prophase.vo;

import lombok.Data;
import net.cdsunrise.wm.prophase.entity.HouseDemolition;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/21
 * Describe:
 */
@Data
public class HouseDemolitionSaveVo {

    /**
     * 房屋拆迁数据
     */
    private HouseDemolition houseDemolition;

    /**
     * 关联模型设局集合
     */
    private List<ProphaseRefModelVo> list;

}
