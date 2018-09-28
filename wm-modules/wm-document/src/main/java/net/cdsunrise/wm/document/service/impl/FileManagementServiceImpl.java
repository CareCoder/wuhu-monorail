package net.cdsunrise.wm.document.service.impl;

import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.PageCondition;
import net.cdsunrise.wm.base.hibernate.Pager;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.util.FileUtils;
import net.cdsunrise.wm.base.web.UserUtils;
import net.cdsunrise.wm.document.client.SystemClient;
import net.cdsunrise.wm.document.entity.FileManagement;
import net.cdsunrise.wm.document.entity.FileManagementAuth;
import net.cdsunrise.wm.document.repository.FileManagementRepository;
import net.cdsunrise.wm.document.service.FileManagementService;
import net.cdsunrise.wm.document.vo.CurrentFolderVo;
import net.cdsunrise.wm.document.vo.FileManagementResultVo;
import net.cdsunrise.wm.document.vo.FileManagementUploadVo;
import net.cdsunrise.wm.document.vo.FolderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/8/1/001
 * Describe:
 */
@Service
public class FileManagementServiceImpl implements FileManagementService {

    @Autowired
    private FileManagementRepository fileManagementRepository;

    @Autowired
    private FileManagementAuthServiceImpl fileManagementAuthServiceImpl;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private SystemClient systemClient;

    /**
     * 上传文件路径前缀
     */
    @Value("${uploadPath}")
    private String uploadUrl;

    /**
     * 访问文件路径前缀
     */
    @Value("${accessPath}")
    private String accessPath;

    /**
     * 上传文件或创建文件夹
     *
     * @param fileManagementUploadVo
     */
    @Override
    public void save(FileManagementUploadVo fileManagementUploadVo) {
        if (fileManagementUploadVo.getType() == 1) {
            //创建文件夹
            FileManagement fileManagement = new FileManagement();
            fileManagement.setFileName(fileManagementUploadVo.getFolderName());
            fileManagement.setType(1);
            fileManagement.setParentId(fileManagementUploadVo.getParentId());
            fileManagement.setSize(0);
            fileManagement.setSuffix("/");
            fileManagement.setUrl("/");
            fileManagement.setUploadPersonId(UserUtils.getUserId());
            fileManagement.setUploadPersonName(UserUtils.getUsername());
            fileManagement.setUploadTime(new Date());
            //文件夹不可分享
            fileManagement.setIsControl(1);
            fileManagementRepository.save(fileManagement);
        } else {
            //上传文件
            try {
                MultipartFile file = fileManagementUploadVo.getFile();
                //原文件名包含后缀
                String fileName = file.getOriginalFilename();
                //文件后缀
                String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                //原文件名不包含后缀
                String fileNamePrefix = fileName.substring(0, fileName.lastIndexOf("."));
                //文件大小 KB
                //double fileSize = new BigDecimal((float)file.getSize() / 1048576).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                long fileSize = file.getSize() / 1024;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int month = calendar.get(Calendar.MONTH) + 1;
                //按月份创建上传文件夹
                uploadUrl = uploadUrl + String.valueOf(month) + "月";
                //数据库存储的url地址
                String url = accessPath + String.valueOf(month) + "月/" + fileName;

                FileUtils.uploadSingleFile(uploadUrl, file);
                FileManagement fileManagement = new FileManagement();
                fileManagement.setFileName(fileNamePrefix);
                fileManagement.setType(2);
                fileManagement.setParentId(fileManagementUploadVo.getParentId());
                fileManagement.setSize(fileSize);
                fileManagement.setSuffix(fileSuffix);
                fileManagement.setUrl(url);
                fileManagement.setUploadPersonId(UserUtils.getUserId());
                fileManagement.setUploadPersonName(UserUtils.getUsername());
                fileManagement.setUploadTime(new Date());
                fileManagement.setIsControl(1);
                FileManagement result = fileManagementRepository.save(fileManagement);
                if (fileManagementUploadVo.getIsControl() == 1 && fileManagementUploadVo.getDeptIdString() != null) {
                    //先判断数据库是否有该文件的权限信息，有的话删除
                    fileManagementAuthServiceImpl.deleteByFileId(result.getId());
                    //保存权限信息
                    List<String> deptIdList = Arrays.asList(fileManagementUploadVo.getDeptIdString().split(","));
                    List<FileManagementAuth> fileManagementAuthList = new ArrayList<>();
                    deptIdList.stream().forEach(String -> {
                        FileManagementAuth temp = new FileManagementAuth();
                        temp.setDeptId(Long.valueOf(String));
                        temp.setFileId(result.getId());
                        fileManagementAuthList.add(temp);
                    });
                    fileManagementAuthServiceImpl.add(fileManagementAuthList);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件夹,文件删除，删除文件夹需要递归文件夹下面所有文件夹和文件
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        FileManagement fileManagement = fileManagementRepository.findOne(id);
        if (fileManagement.getType() == 2) {
            //删除文件
            fileManagementAuthServiceImpl.deleteByFileId(id);
            fileManagementRepository.delete(id);
        } else {
            //删除文件夹
            List<Long> deleteIdList = new ArrayList<>();

        }
    }

    @Override
    public FileManagement getOne(Long id) {
        return fileManagementRepository.findOne(id);
    }

    /**
     * 根据当前文件夹的id查询当前文件夹下的文件夹和文件，父级文件夹id为0时代表是根目录
     *
     * @param parentFolderId 当前点击的文件夹id
     * @return
     */
    @Override
    public CurrentFolderVo getAll(Long parentFolderId) {
        CurrentFolderVo currentFolderVo = new CurrentFolderVo();
        List<FileManagement> fileManagementList = fileManagementRepository.findByUploadPersonIdAndParentIdOrderByTypeAsc(UserUtils.getUserId(), parentFolderId);
        currentFolderVo.setList(fileManagementList);
        //查询当前的文件夹的路径集合   （文件夹名称和id）
        List<FolderVo> folderVoList = new ArrayList<>();

        if (parentFolderId == 0) {
            //当点击的是根目录时
            FolderVo vo = new FolderVo();
            vo.setFolderId(0l);
            vo.setFolderName("根目录");
            folderVoList.add(vo);
            currentFolderVo.setFolderVoList(folderVoList);
            return currentFolderVo;
        } else {
            List<FolderVo> result = recursionParentFolder(folderVoList, parentFolderId);
            //为了前端遍历顺序，在递归之后添加根目录信息
            FolderVo vo = new FolderVo();
            vo.setFolderId(0l);
            vo.setFolderName("根目录");
            result.add(vo);
            currentFolderVo.setFolderVoList(result);
            return currentFolderVo;
        }
    }

    /**
     * 分享资料页面文件列表查询--分页
     *
     * @param condition
     * @return
     */
    @Override
    public Pager<FileManagementResultVo> shareFilePage(PageCondition condition) {
        Long deptId = systemClient.fetchUserDeptIdByUserId(UserUtils.getUserId()).getDeptId();
        QueryHelper helper = new QueryHelper("id,fileName,type,parentId,suffix,size,url,uploadPersonId,uploadPersonName,uploadTime,isControl",
                " (select w1.id,w1.file_name as fileName,w1.type,w1.parent_id as parentId,w1.suffix,w1.size,w1.url,w1.upload_person_id as uploadPersonId,w1.upload_person_name as uploadPersonName,w1.upload_time as uploadTime,is_control as isControl from wm_data_file_manage w1 where  w1.id" +
                        " in (select file_id from wm_data_file_manage_auth where dept_id = " + deptId + ") ) u")
                .setPageCondition(condition);
        Pager<FileManagementResultVo> pager = commonDAO.findPager(helper,FileManagementResultVo.class);
        return pager;
    }

    /**
     * 向上递归遍历父级文件夹树
     *
     * @param folderVoList
     * @param id           当前文件夹id
     * @return
     */
    public List<FolderVo> recursionParentFolder(List<FolderVo> folderVoList, Long id) {
        FileManagement fileManagement = fileManagementRepository.findOne(id);
        FolderVo vo = new FolderVo();
        vo.setFolderId(fileManagement.getId());
        vo.setFolderName(fileManagement.getFileName());
        folderVoList.add(vo);
        if (fileManagement.getParentId() != 0) {
            //如果遍历到还不是根目录，继续遍历
            recursionParentFolder(folderVoList, fileManagement.getParentId());
        }
        return folderVoList;
    }
}

