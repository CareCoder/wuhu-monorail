package net.cdsunrise.wm.quality.controller;

import io.swagger.annotations.Api;
import net.cdsunrise.wm.quality.entity.Teamwork;
import net.cdsunrise.wm.quality.service.TeamworkService;
import net.cdsunrise.wm.quality.vo.TeamworkVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/***
 * @author gechaoqing
 * 线性测量
 */
@RestController
@Api("协同工作")
@RequestMapping("/teamwork")
public class TeamworkController {
    @Resource
    private TeamworkService teamworkService;

    @GetMapping("/list")
    public List<TeamworkVo> list() {
        return teamworkService.list();
    }

    @GetMapping("/query")
    public List<TeamworkVo> query(Teamwork teamwork) {
        return teamworkService.query(teamwork);
    }

    @GetMapping("/delete")
    public void delete(Long[] ids) {
        teamworkService.delete(ids);
    }

    @PostMapping("/upload")
    public void upload(MultipartFile file, Teamwork teamwork) {
        teamworkService.upload(file, teamwork);
    }

    @GetMapping("/zip")
    public ResponseEntity<byte[]> zip(Long[] ids) {
        return teamworkService.zip(ids);
    }
}
