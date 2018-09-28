package net.cdsunrise.wm.document.entity;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import net.cdsunrise.wm.base.hibernate.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/1/001
 * Describe:
 */
@Data
@Entity
@Table(name = "wm_data_file_manage_auth")
public class FileManagementAuth extends BaseEntity {

    @ApiParam("文件id")
    private Long fileId;

    @ApiParam("部门id")
    private Long deptId;
}
