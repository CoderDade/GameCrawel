package com.dade.crawel.gamecrawel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LOLGameInfoMsgDTO implements Serializable{
    String seconds;
    String team;
    LOLGameInfoPositionDTO positionDTO;
    String killer;
    String victim;
    List<String> assisting;
}
