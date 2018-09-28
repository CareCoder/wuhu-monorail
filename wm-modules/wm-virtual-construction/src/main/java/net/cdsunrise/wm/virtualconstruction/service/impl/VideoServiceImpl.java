package net.cdsunrise.wm.virtualconstruction.service.impl;

import net.cdsunrise.wm.virtualconstruction.bo.FileResourceBo;
import net.cdsunrise.wm.virtualconstruction.bo.VideoBo;
import net.cdsunrise.wm.virtualconstruction.client.FileResourceClient;
import net.cdsunrise.wm.virtualconstruction.entity.Video;
import net.cdsunrise.wm.virtualconstruction.repository.VideoRepository;
import net.cdsunrise.wm.virtualconstruction.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private FileResourceClient fileResourceClient;

    @Override
    public List<VideoBo> getList() {
        List<Video> videos = videoRepository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
        List<VideoBo> bos = new ArrayList<>(videos.size());
        videos.forEach(v ->
                bos.add(convertToBo(v))
        );
        return bos;
    }

    @Override
    public VideoBo get(Long id) {
        Video video = videoRepository.findOne(id);
        return convertToBo(video);
    }

    private VideoBo convertToBo(Video video) {
        FileResourceBo cover = fileResourceClient.get(video.getCoverImageId());
        FileResourceBo v = fileResourceClient.get(video.getVideoId());
        VideoBo bo = new VideoBo();
        BeanUtils.copyProperties(video, bo);
        bo.setCoverImageUrl(cover.getUrl());
        bo.setVideoUrl(v.getUrl());
        return bo;
    }
}
