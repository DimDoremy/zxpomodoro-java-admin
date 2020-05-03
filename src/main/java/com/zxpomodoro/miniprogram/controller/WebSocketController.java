package com.zxpomodoro.miniprogram.controller;

import com.zxpomodoro.miniprogram.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@Component
@ServerEndpoint("/zxwebsocket/{openid}")
public class WebSocketController {
    @Resource
    private RedisUtils redisUtils;

    @OnOpen
    public void onOpen(@PathParam(value = "openid")String openid, Session session) {
        log.info("");
    }
}
