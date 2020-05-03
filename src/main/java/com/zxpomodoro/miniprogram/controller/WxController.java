package com.zxpomodoro.miniprogram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RestController
public class WxController {
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/api/openid")
    public String getOpenid(@RequestBody String js_code){
        try {
            Map codeMap = mapper.readValue(js_code, Map.class);
            String APPID = "wxb5ffc058d0c82c22";
            String SECRET = "27735d75d1fe375f83c99b993f541f28";
            String wxLogin = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret="+ SECRET + "&js_code=" + codeMap.get("code").toString() + "&grant_type=authorization_code";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> entity = restTemplate.getForEntity(wxLogin, String.class);
            return entity.getBody();
        } catch (JsonProcessingException e) {
            return "{\"error\":" + e.getMessage() + "}";
        }
    }
}
