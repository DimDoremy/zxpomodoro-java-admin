package com.zxpomodoro.miniprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxpomodoro.miniprogram.mapper.RecruitMapper;
import com.zxpomodoro.miniprogram.model.RecruitPublic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RecruitController {
    @Resource
    private RecruitMapper recruitMapper;

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/api/all_rp")
    public String allRecruit() {
        List<RecruitPublic> recruitPublicList = recruitMapper.selectAllRecruit();
        return recruitPublicList.toString();
    }

    @PostMapping("/api/rp_by_openid")
    public String selectByMission(@RequestBody String jsonId) {
        try {
            var maps = mapper.readValue(jsonId, Map.class);
            var recruitPublic = recruitMapper.selectByMission((String)maps.get("mission"));
            log.info(recruitPublic.getMission() + ": select success");
            return mapper.writeValueAsString(recruitPublic);
        } catch (Exception e) {
            return "{\"error\":" + e.getMessage() + "}";
        }
    }

    @PostMapping("/api/update_rp")
    public boolean updateRecruit(@RequestBody String jsonData) {
        try {
            var recruit = mapper.readValue(jsonData, RecruitPublic.class);
            recruitMapper.updateRecruit(recruit);
            log.info(recruit.getMission() + ": update success");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @PostMapping("/api/insert_rp")
    public int insertRecruit(@RequestBody String jsonData) {
        try {
            var recruit = mapper.readValue(jsonData, RecruitPublic.class);
            log.info(recruit.getMission() + ": insert success");
            return recruitMapper.insertRecruit(recruit);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    @PostMapping("/api/delete_rp")
    public int deleteRecruit(@RequestBody String jsonId) {
        try {
            var maps = mapper.readValue(jsonId, Map.class);
            var recruit = recruitMapper.selectByMission((String) maps.get("mission"));
            int returns = recruitMapper.deleteByMission((String) maps.get("mission"));
            log.info(recruit.getMission() + ": select success");
            return returns;
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
