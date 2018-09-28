package net.cdsunrise.wm.quality.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

/***
 * @author gechaoqing
 * 墩坐标标准
 */
@Data
@Entity
@Table(name = "wm_pier_coordinate_standard")
public class PierCoordinateStandard {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JsonBackReference
    private GirderPier pier;
    @Column(columnDefinition = "double(25,9)")
    private Double x;
    /***
     * x偏差
     */
    @Column(columnDefinition = "double(25,9)")
    private Double xDeviation;
    @Column(columnDefinition = "double(25,9)")
    private Double y;
    /***
     * y偏差
     */
    @Column(columnDefinition = "double(25,9)")
    private Double yDeviation;
    @Column(columnDefinition = "double(25,9)")
    private Double z;
    /***
     * y偏差
     */
    @Column(columnDefinition = "double(25,9)")
    private Double zDeviation;

    @Override
    public String toString() {
        return "PierCoordinateStandard{" +
                "id=" + id +
                ", pier=" + pier.getGirderCode()+"-"+pier.getPierCode() +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", xDeviation=" + xDeviation +
                ", yDeviation=" + yDeviation +
                ", zDeviation=" + zDeviation +
                '}';
    }
}
