package com.dade.crawel.gamecrawel.service;

import com.alibaba.fastjson.JSON;
import com.dade.crawel.gamecrawel.dal.entity.LOLUserEntity;
import com.dade.crawel.gamecrawel.dto.LOLUserInfoDTO;
import com.dade.crawel.gamecrawel.dto.LOLUserMsgDTO;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:http.properties")
public class LOLUserInfoService {
    @Value("${user_info_head}")
    private String user_info_head;
    @Value("${user_info_tail}")
    private String user_info_tail;

    public LOLUserInfoDTO getUserInfoDTOByUserId(String userId) {
        if (StringUtils.isEmpty(userId)){
            return null;
        }
        try {
            CloseableHttpClient httpClient= HttpClients.createDefault();
            HttpGet httpget = new HttpGet(user_info_head+userId+user_info_tail);
            httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
            httpget.setHeader("Content-Type", "text/html;charset=utf-8");
            CloseableHttpResponse response = httpClient.execute(httpget);
            HttpEntity httpEntity= response.getEntity();
            String strResult= EntityUtils.toString(httpEntity);

            // transform json to dto
            if(StringUtils.isEmpty(strResult)){
                return null;
            }
            strResult = strResult.replace("var retObj=", "");
            LOLUserInfoDTO lolUserInfoDTO = JSON.parseObject(strResult, LOLUserInfoDTO.class);
            return lolUserInfoDTO;
        }catch (Exception e){
            return null;
        }
    }

    public List<LOLUserEntity> transformDTOToEntity(List<LOLUserInfoDTO> infos){
        if (CollectionUtils.isEmpty(infos)){
            return Collections.emptyList();
        }
        return infos.stream()
                .filter(info->info.getMsg()!=null)
                .map(info-> {
                    LOLUserMsgDTO msg = info.getMsg();
                    LOLUserEntity entity = new LOLUserEntity();
                    BeanUtils.copyProperties(msg, entity);
                    entity.setUseId(msg.getId());
                    try {
                        entity.setName(URLDecoder.decode(msg.getName(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return entity;
                })
                .collect(Collectors.toList());
    }

}
