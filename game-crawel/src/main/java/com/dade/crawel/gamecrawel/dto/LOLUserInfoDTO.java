package com.dade.crawel.gamecrawel.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LOLUserInfoDTO implements Serializable {
    String status;
    LOLUserMsgDTO msg;
}
