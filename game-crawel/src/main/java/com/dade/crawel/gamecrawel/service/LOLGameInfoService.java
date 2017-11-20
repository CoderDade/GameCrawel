package com.dade.crawel.gamecrawel.service;

import com.alibaba.fastjson.JSON;
import com.dade.crawel.gamecrawel.dto.LOLGameInfoDTO;
import com.google.common.collect.Lists;
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

import java.util.List;

@Service
@PropertySource("classpath:http.properties")
public class LOLGameInfoService {
    @Value("${game_info_head}")
    public String game_info_head;
    @Value("${game_info_tail}")
    public String game_info_tail;

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
//            List<GameDetailEntity> res = Lists.newArrayList();
//            userGameInfoEntity.getMsg().forEach(msg -> {
//                if (msg.getAssisting() == null || msg.getAssisting().size() == 0) {
//                    GameDetailEntity entity = new GameDetailEntity();
//                    BeanUtils.copyProperties(msg, entity);
//                    entity.setX(msg.getPosition().getX());
//                    entity.setY(msg.getPosition().getY());
//                    res.add(entity);
//                    return;
//                }else{
//                    GameDetailEntity entity = new GameDetailEntity();
//                    BeanUtils.copyProperties(msg, entity);
//                    entity.setX(msg.getPosition().getX());
//                    entity.setY(msg.getPosition().getY());
//                    String join = String.join(",", msg.getAssisting());
//                    entity.setAssistings(join);
//                    res.add(entity);
//                }
//            });
        }catch (Exception e){
            return null;
        }
    }

}
