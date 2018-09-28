package net.cdsunrise.wm.beamfield.repository;

import net.cdsunrise.wm.beamfield.entity.RelatedDrawings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: WangRui
 * Date: 2018/5/28
 * Describe:
 */
public interface RelatedDrawingsRepository extends JpaRepository<RelatedDrawings, Long> {

    List<RelatedDrawings> findByBeamLengthAndBeamTypeAndPierHeight(String beamLength, String beamType, String pierHeight);

    List<RelatedDrawings> findByBeamTypeAndPierHeight(String beamType, String pierHeight);

    RelatedDrawings findFirstByDrawingsType(Integer drawingsType);

    RelatedDrawings findFirstByBeamLengthAndBeamTypeAndPierHeightAndDrawingsType(String beamLength, String beamType, String pierHeight, Integer drawingsType);

    RelatedDrawings findFirstByBeamTypeAndPierHeightAndDrawingsType(String beamType, String pierHeight, Integer drawingsType);

    @Transactional
    void deleteByBeamTypeAndPierHeightAndDrawingsType(String beamType, String pierHeight, int type);
}
