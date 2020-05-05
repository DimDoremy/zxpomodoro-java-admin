package com.zxpomodoro.miniprogram.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websocket/{openid}")
public class WebsocketServer {
    private static int onlineCount = 0;
    private static final ConcurrentHashMap<String, WebsocketServer> webSocketMap = new ConcurrentHashMap<>();
    private Session session;
    private String openid = "";
    ObjectMapper mapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session, @PathParam("openid") String openid) {
        this.session = session;
        this.openid = openid;
        if (webSocketMap.containsKey(openid)) {
            webSocketMap.remove(openid);
            webSocketMap.put(openid, this);
        } else {
            webSocketMap.put(openid, this);
            addOnlineCount();
        }
        log.info(openid + "已创建。连接数 '" + getOnlineCount() + "'");
        try {
            sendMessage("连接成功");
        } catch (Exception e) {
            log.error(openid + " : 连接异常");
            log.error(e.getMessage());
        }
    }

    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(openid)) {
            webSocketMap.remove(openid);
            subOnlineCount();
        }
        log.info(openid + "已登出。连接数 '" + getOnlineCount() + "'");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(openid + " : " + message);
        if (StringUtils.isNotBlank(message)) {
            try {
                Map<String, String> jsonMap = new HashMap<>();
                jsonMap.put("openid", this.openid);
                jsonMap.put("message", message);
                String jsonMessage = mapper.writeValueAsString(jsonMap);
                if (StringUtils.isNotBlank(jsonMap.get("openid")) && webSocketMap.containsKey(openid)) {
                    webSocketMap.get(openid).sendMessage(jsonMessage);
                } else {
                    log.warn(openid + "不在线");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(this.openid + " : " + error.getMessage());
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void sendInfo(String message, @PathParam("openid") String openid) throws IOException {
        log.info(openid + " : " + message);
        if (StringUtils.isNotBlank(openid) && webSocketMap.containsKey(openid)) {
            webSocketMap.get(openid).sendMessage(message);
        } else {
            log.warn(openid + "不在线");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebsocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebsocketServer.onlineCount--;
    }
}
