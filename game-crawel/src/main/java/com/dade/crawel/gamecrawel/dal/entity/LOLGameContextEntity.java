package com.dade.crawel.gamecrawel.dal.entity;

import lombok.Data;

@Data
public class LOLGameContextEntity {
    String gameId;
    String platformId;
    String champion;
    String queue;
    String season;
    String timestamp;
    String lane;
    String role;
}


