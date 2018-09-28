package net.cdsunrise.wm.schedule.service.impl;

import net.cdsunrise.wm.schedule.entity.DelayReason;
import net.cdsunrise.wm.schedule.repository.DelayReasonRepository;
import net.cdsunrise.wm.schedule.service.DelayReasonService;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author gechaoqing
 * 进度延迟原因服务
 */
@Service
public class DelayReasonServiceImpl implements DelayReasonService {
    private DelayReasonRepository delayReasonRepository;
    public DelayReasonServiceImpl(DelayReasonRepository delayReasonRepository){
        this.delayReasonRepository = delayReasonRepository;
    }
    @Override
    public List<DelayReason> getByParentId(Long parentId) {
        return delayReasonRepository.findByParentId(parentId);
    }

    @Override
    public List<DelayReason> getRoot() {
        return delayReasonRepository.findByParentIdIsNull();
    }
}
