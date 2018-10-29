package net.cdsunrise.wm.quality.service;

import net.cdsunrise.wm.quality.entity.NewsModule;

import java.util.List;

public interface NewsModuleService {
    List<NewsModule> query(String info);

    List<NewsModule> list();
}
