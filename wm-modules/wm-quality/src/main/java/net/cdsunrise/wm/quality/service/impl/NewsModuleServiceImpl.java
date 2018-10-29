package net.cdsunrise.wm.quality.service.impl;

import net.cdsunrise.wm.quality.entity.NewsModule;
import net.cdsunrise.wm.quality.repostory.NewsModuleRepository;
import net.cdsunrise.wm.quality.service.NewsModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsModuleServiceImpl implements NewsModuleService {
    @Resource
    private NewsModuleRepository newsModuleRepository;


    @Override
    public List<NewsModule> query(String info) {
        return newsModuleRepository.findByDescriptionLike("%" + info + "%");
    }

    @Override
    public List<NewsModule> list() {
        return newsModuleRepository.findAll();
    }
}
