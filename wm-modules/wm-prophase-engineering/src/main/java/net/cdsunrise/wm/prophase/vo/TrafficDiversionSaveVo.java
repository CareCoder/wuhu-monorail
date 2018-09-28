package net.cdsunrise.wm.prophase.vo;

import lombok.Data;
import net.cdsunrise.wm.prophase.entity.TrafficDiversion;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/21
 * Describe:
 */
@Data
public class TrafficDiversionSaveVo {

    private TrafficDiversion trafficDiversion;

    private List<ProphaseRefModelVo> list;
}
