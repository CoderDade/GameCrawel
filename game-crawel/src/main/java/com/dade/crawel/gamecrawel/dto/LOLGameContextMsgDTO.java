package com.dade.crawel.gamecrawel.dto;

import com.dade.crawel.gamecrawel.dal.entity.LOLGameContextEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LOLGameContextMsgDTO implements Serializable {
    List<LOLGameContextEntity> games;
}
