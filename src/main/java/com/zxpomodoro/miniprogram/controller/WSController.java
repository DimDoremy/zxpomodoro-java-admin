package com.zxpomodoro.miniprogram.controller;

import com.zxpomodoro.miniprogram.tools.WebsocketServer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WSController {
    @GetMapping(value = "/push/{openid}:{message}")
    public ResponseEntity<String> pushToWeb(@PathVariable String message,
                                            @PathVariable String openid) throws IOException {
        WebsocketServer.sendInfo(message,openid);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }
}
