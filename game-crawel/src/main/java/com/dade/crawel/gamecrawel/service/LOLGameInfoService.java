package com.dade.crawel.gamecrawel.service;

import com.alibaba.fastjson.JSON;
import com.dade.crawel.gamecrawel.dal.entity.LOLGameInfoEntity;
import com.dade.crawel.gamecrawel.dto.LOLGameInfoDTO;
import com.dade.crawel.gamecrawel.dto.LOLGameInfoMsgDTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:http.properties")
public class LOLGameInfoService {
    @Value("${game_info_head}")
    private String game_info_head;
    @Value("${game_info_tail}")
    private String game_info_tail;

    public LOLGameInfoDTO getUserIdByGameId(String gameId)  {
        if (StringUtils.isEmpty(gameId)){
            return null;
        }
        try {
            String url = game_info_head+gameId+game_info_tail;
            CloseableHttpClient httpClient= HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
            CloseableHttpResponse response = httpClient.execute(httpget);
            HttpEntity httpEntity= response.getEntity();
            String strResult= EntityUtils.toString(httpEntity);

            // transfor json to dto
            if (StringUtils.isEmpty(strResult)){
                return null;
            }
            strResult = strResult.replace("var championKill=","");

            LOLGameInfoDTO gameInfoDTO = JSON.parseObject(strResult, LOLGameInfoDTO.class);
            return gameInfoDTO;
        }catch (Exception e){
            return null;
        }
    }

    public List<LOLGameInfoEntity> transformDTOToEntity(LOLGameInfoDTO gameInfoDTO){
        if (gameInfoDTO == null || CollectionUtils.isEmpty(gameInfoDTO.getMsg())){
            return Collections.emptyList();
        }

        List<LOLGameInfoMsgDTO> msgs = gameInfoDTO.getMsg();
        return msgs.stream().map(msg -> {
            LOLGameInfoEntity entity = new LOLGameInfoEntity();
            BeanUtils.copyProperties(msg, entity);
            entity.setX(msg.getPositionDTO() == null ? "" : msg.getPositionDTO().getX());
            entity.setY(msg.getPositionDTO() == null ? "" : msg.getPositionDTO().getY());
            entity.setAssistings(org.apache.commons.lang3.StringUtils.join(msg.getAssisting()));
            return entity;
        }).collect(Collectors.toList());

    }


}
