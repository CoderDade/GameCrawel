package com.dade.crawel.gamecrawel.service;

import com.dade.crawel.gamecrawel.dal.dao.LOLUserInfoDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLUserEntity;
import com.dade.crawel.gamecrawel.dto.LOLUserInfoDTO;
import com.dade.crawel.gamecrawel.pool.LOLCrawelPool;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class LOLCrawelParallelService {


    // todo thread and single design

    @Autowired
    LOLUserInfoService userInfoService;

    @Autowired
    LOLUserInfoDao userInfoDao;

    public void consumeUserId(LOLCrawelPool pool){
        while (true){
            String userId = pool.getUserId();
            LOLUserInfoDTO userInfoDTO = userInfoService.getUserInfoDTOByUserId(userId);
            List<LOLUserEntity> lolUserEntities = userInfoService.transformDTOToEntity(Lists.newArrayList(userInfoDTO));
            userInfoDao.insertUserInfoList(lolUserEntities);
        }
    }
    public void produceGameId(LOLCrawelPool pool){
        while (true){
            pool.getUserId()
        }
    }


}
