package com.dade.crawel.gamecrawel.threads;

import com.dade.crawel.gamecrawel.dal.dao.LOLUserInfoDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLUserEntity;
import com.dade.crawel.gamecrawel.dto.LOLUserInfoDTO;
import com.dade.crawel.gamecrawel.pool.LOLUserIdPool;
import com.dade.crawel.gamecrawel.service.LOLUserInfoService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LOLUserIdConsumer implements Runnable {

    @Autowired
    LOLUserInfoService userInfoService;

    @Autowired
    LOLUserInfoDao userInfoDao;

    @Override
    public void run() {
//        testRun();
        consumeUserId();
    }

    private void testRun(){
        int count = 0;
        while(count<100){
            System.out.println("userIdConsumer: " + count++);
        }
    }

    private void consumeUserId(){
        LOLUserIdPool pool = LOLUserIdPool.getInstance();
        while (true){
            String userId = pool.getUserId();
            LOLUserInfoDTO userInfoDTO = userInfoService.getUserInfoDTOByUserId(userId);
            List<LOLUserEntity> lolUserEntities = userInfoService.transformDTOToEntity(Lists.newArrayList(userInfoDTO));
            userInfoDao.insertUserInfoList(lolUserEntities);
            System.out.println("----------insert into db start----------");
            lolUserEntities.forEach(System.out::println);
            System.out.println("----------insert into db end----------");
        }
    }


}
