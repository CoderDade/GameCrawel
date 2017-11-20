package com.dade.crawel.gamecrawel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LOLGameContextDTO implements Serializable {
    String status;
    LOLGameContextMsgDTO msg;
}
