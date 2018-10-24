package net.cdsunrise.wm.quality.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import net.cdsunrise.wm.base.dao.CommonDAO;
import net.cdsunrise.wm.base.hibernate.QueryHelper;
import net.cdsunrise.wm.base.util.BeanUtilIgnore;
import net.cdsunrise.wm.quality.entity.Teamwork;
import net.cdsunrise.wm.quality.feign.FileResourceFeign;
import net.cdsunrise.wm.quality.repostory.TeamworkRepository;
import net.cdsunrise.wm.quality.service.TeamworkService;
import net.cdsunrise.wm.quality.vo.FileResourceBo;
import net.cdsunrise.wm.quality.vo.TeamworkVo;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TeamworkServiceImpl implements TeamworkService {
    @Resource
    private TeamworkRepository teamworkRepository;

    @Resource
    private FileResourceFeign fileResourceFeign;

    @Resource
    private CommonDAO commonDAO;

    @Override
    public List<TeamworkVo> list() {
        return teamworkRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public void upload(MultipartFile file, Teamwork teamwork) {
        String uuid = fileResourceFeign.upload(file);
        teamwork.setFileUuid(uuid);
        teamwork.setCreateTime(new Date());
        teamwork.setModifyTime(new Date());
        teamworkRepository.save(teamwork);
    }

    @Override
    public List<TeamworkVo> query(Teamwork teamwork) {
        QueryHelper queryHelper = new QueryHelper(Teamwork.class, "t");
        queryHelper
                .addCondition(!Objects.isNull(teamwork.getId()), "t.id = ?", teamwork.getId())
                .addCondition(!Objects.isNull(teamwork.getFileUuid()), "t.fileUuid = ?", teamwork.getFileUuid())
                .addCondition(!Objects.isNull(teamwork.getName()), "t.name = ?", teamwork.getName())
                .addCondition(!Objects.isNull(teamwork.getStatus()), "t.status = ?", teamwork.getStatus())
                .addCondition(!Objects.isNull(teamwork.getWorkPointId()), "t.workPointId = ?", teamwork.getWorkPointId())
                .useNativeSql(false);
        List<Teamwork> list = commonDAO.findList(queryHelper);
        return list.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<byte[]> zip(Long[] ids) {
        List<String> uuidList = new ArrayList<>();
        for (Long id : ids) {
            Teamwork one = teamworkRepository.findOne(id);
            uuidList.add(one.getFileUuid());
        }
        String[] uuids = new String[uuidList.size()];
        uuidList.toArray(uuids);
        return fileResourceFeign.downloadByUuid(uuids);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            Teamwork one = teamworkRepository.findOne(id);
            fileResourceFeign.deleteByUuid(one.getFileUuid());
            teamworkRepository.delete(id);
        }
    }

    @Override
    public void update(Teamwork teamwork) {
        Teamwork one = teamworkRepository.findOne(teamwork.getId());
        one.setModifyTime(new Date());
        BeanUtilIgnore.copyPropertiesIgnoreNull(teamwork, one);
        teamworkRepository.save(one);
    }

    private TeamworkVo convert(Teamwork teamwork) {
        TeamworkVo vo = new TeamworkVo();
        BeanUtils.copyProperties(teamwork, vo);
        String uuid = teamwork.getFileUuid();
        FileResourceBo bo = fileResourceFeign.getByUuid(uuid);
        vo.setFileUrl(bo.getUrl());
        return vo;
    }
}
