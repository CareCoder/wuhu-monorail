package net.cdsunrise.wm.schedule.service;

import net.cdsunrise.wm.schedule.entity.DelayReason;

import java.util.List;

/***
 * @author gechaoqing
 * 进度延迟原因服务
 */
public interface DelayReasonService {
    /***
     * 根据父级ID获取延迟原因集合
     * @param parentId 父级ID
     * @return 延迟原因集合
     */
    List<DelayReason> getByParentId(Long parentId);

    /***
     * 获取根节点延迟原因
     * @return 延迟原因集合
     */
    List<DelayReason> getRoot();
}
