package net.cdsunrise.wm.beamfield.vo;

import lombok.Data;

/**
 * Author: WangRui
 * Date: 2018/5/30
 * Describe:
 */
@Data
public class ResourceVo {

    /**
     * 资源类型（1菜单、2事件、3授权）
     */
    private Integer type;

    /**
     * 链接
     */
    private String path;
}
