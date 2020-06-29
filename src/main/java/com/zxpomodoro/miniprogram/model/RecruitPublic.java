package com.zxpomodoro.miniprogram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitPublic {
    private String mission;
    private int isopen;
    private int isspecial;
    private int rank;
    private String title;
    private String description;
}
