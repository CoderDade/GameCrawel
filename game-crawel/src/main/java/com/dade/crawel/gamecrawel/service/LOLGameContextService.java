package com.dade.crawel.gamecrawel.service;

import com.alibaba.fastjson.JSON;
import com.dade.crawel.gamecrawel.dal.entity.LOLGameContextEntity;
import com.dade.crawel.gamecrawel.dto.LOLGameContextDTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@PropertySource("classpath:http.properties")
public class LOLGameContextService {
    @Value("${game_context_head}")
    private String game_context_head;
    @Value("${game_context_tail}")
    private String game_context_tail;

    public LOLGameContextDTO getGameContextDTOByUserId(String userId){
        if (StringUtils.isEmpty(userId)){
            return null;
        }
        try {
            CloseableHttpClient httpClient= HttpClients.createDefault();
            String url = game_context_head+userId+game_context_tail;
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
            CloseableHttpResponse response = httpClient.execute(httpget);
            HttpEntity httpEntity= response.getEntity();
            String strResult= EntityUtils.toString(httpEntity);

            //transfor json to dto
            if (StringUtils.isEmpty(strResult)){
                return null;
            }
            strResult = strResult.replace("var matchList=","");
            LOLGameContextDTO gameContextDTO = JSON.parseObject(strResult, LOLGameContextDTO.class);
            return gameContextDTO;
        }catch (Exception e){
            return null;
        }
    }

    public List<LOLGameContextEntity> transformDTOToEntity(LOLGameContextDTO gameContextDTO){
        if(gameContextDTO == null || gameContextDTO.getMsg() == null)
            return Collections.emptyList();

        return gameContextDTO.getMsg().getGames();
    }


}
