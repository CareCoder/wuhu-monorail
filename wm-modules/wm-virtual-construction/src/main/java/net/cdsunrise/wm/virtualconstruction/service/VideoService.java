package net.cdsunrise.wm.virtualconstruction.service;

import net.cdsunrise.wm.virtualconstruction.bo.VideoBo;

import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
public interface VideoService {
    List<VideoBo> getList();

    VideoBo get(Long id);
}
