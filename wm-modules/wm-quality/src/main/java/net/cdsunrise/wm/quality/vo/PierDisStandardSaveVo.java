package net.cdsunrise.wm.quality.vo;

import lombok.Data;

import java.util.List;


/***
 * @author gechaoqing
 * 偏差值保存
 */
@Data
public class PierDisStandardSaveVo {
    List<PierDisStandardDeviationVo> deviation;
}
