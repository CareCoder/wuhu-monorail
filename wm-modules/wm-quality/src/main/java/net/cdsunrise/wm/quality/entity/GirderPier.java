package net.cdsunrise.wm.quality.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/***
 * @author gechaoqing
 * 梁
 */
@Data
@Entity
@Table(name="wm_girder_pier")
public class GirderPier {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(
            mappedBy = "pier",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PierDisStandard> disStandardList = new ArrayList<>();

    public void addDisStandard(PierDisStandard disStandard){
        disStandardList.add(disStandard);
        disStandard.setPier(this);
    }
    /**
     * 梁号
     */
    private String girderCode;
    /***
     * 墩编号
     */
    private String pierCode;
    /***
     * 墩里程端大小
     * 1 - 小
     * 2 - 大
     */
    private int pierSize;

    @OneToOne(
            mappedBy = "pier",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private PierCoordinateStandard coordinateStandard;

    public void setCoordinateStandard(PierCoordinateStandard coordinateStandard){
        if (coordinateStandard == null) {
            if (this.coordinateStandard != null) {
                this.coordinateStandard.setPier(null);
            }
        }
        else {
            coordinateStandard.setPier(this);
        }
        this.coordinateStandard = coordinateStandard;
    }

    @Transient
    private GirderPier inverse;

}
