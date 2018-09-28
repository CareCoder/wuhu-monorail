package net.cdsunrise.wm.prophase.vo;

import lombok.Data;
import net.cdsunrise.wm.prophase.entity.PeripheralRisk;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/21
 * Describe:
 */
@Data
public class PeripheralRiskSaveVo {

    private PeripheralRisk peripheralRisk;

    private List<ProphaseRefModelVo> list;

}
