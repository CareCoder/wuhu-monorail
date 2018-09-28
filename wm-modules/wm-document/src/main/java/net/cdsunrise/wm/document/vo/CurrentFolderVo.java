package net.cdsunrise.wm.document.vo;

import lombok.Data;
import net.cdsunrise.wm.document.entity.FileManagement;

import java.util.List;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/2/002
 * Describe:
 */
@Data
public class CurrentFolderVo {

    private List<FileManagement> list;

    private List<FolderVo> folderVoList;
}
