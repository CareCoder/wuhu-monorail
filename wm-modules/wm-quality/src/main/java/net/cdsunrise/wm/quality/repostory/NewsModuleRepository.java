package net.cdsunrise.wm.quality.repostory;

import net.cdsunrise.wm.quality.entity.NewsModule;
import net.cdsunrise.wm.quality.entity.WorkPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsModuleRepository extends JpaRepository<NewsModule, Long> {
    List<NewsModule> findByDescriptionLike(String info);
}
