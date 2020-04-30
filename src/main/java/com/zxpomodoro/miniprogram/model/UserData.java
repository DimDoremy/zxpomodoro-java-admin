package com.zxpomodoro.miniprogram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户数据类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private String openid;
    private String nickname;
    private String avatarurl;
    private int time;
    private int soul;
    private int lifepoint;
    private int experience;
}
