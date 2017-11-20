package com.dade.crawel.gamecrawel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LOLGameInfoDTO implements Serializable {
    String status;
    List<LOLGameInfoMsgDTO> msg;
}
