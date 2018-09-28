package net.cdsunrise.wm.quality.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

/***
 * @author gechaoqing
 * 墩平距和高差数据
 */
@Data
@Entity
@Table(name = "wm_pier_dis_standard")
public class PierDisStandard {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pier_id")
    @JsonBackReference
    private GirderPier pier;
    /***
     * 1 临时支撑数据
     * 2 断面数据
     */
    private int type;
    /***
     * 序号
     */
    private int num;
    /***
     * 平距
     */
    @Column(columnDefinition = "double(25,5)")
    private Double flatDis;
    /***
     * 高差
     */
    @Column(columnDefinition = "double(25,5)")
    private Double heightDiff;

    /***
     * 平距偏差
     */
    @Column(columnDefinition = "double(25,5)")
    private Double flatDeviation;
    /***
     * 高度偏差
     */
    @Column(columnDefinition = "double(25,5)")
    private Double heightDeviation;

    @Override
    public String toString() {
        return "PierDisStandard{" +
                "id=" + id +
                ", pier=" + (pier!=null?(pier.getGirderCode()+"-"+pier.getPierCode()):"") +
                ", type=" + type +
                ", num=" + num +
                ", flatDis=" + flatDis +
                ", heightDiff=" + heightDiff +
                ", flatDeviation=" + flatDeviation +
                ", heightDeviation=" + heightDeviation +
                '}';
    }
}
