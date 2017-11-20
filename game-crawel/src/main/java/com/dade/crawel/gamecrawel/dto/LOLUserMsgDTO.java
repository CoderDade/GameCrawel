package com.dade.crawel.gamecrawel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LOLUserMsgDTO implements Serializable {
    String id;
    String name;
    String area;
    String icon;
    String lasted;
    String tier;
    String rank;
}
