package com.dade.crawel.gamecrawel.service;

import com.dade.crawel.gamecrawel.dal.dao.LOLUserInfoDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLUserEntity;
import com.dade.crawel.gamecrawel.dto.LOLUserInfoDTO;
import com.dade.crawel.gamecrawel.pool.LOLUserIdPool;
import com.dade.crawel.gamecrawel.threads.LOLGameIdConsumer;
import com.dade.crawel.gamecrawel.threads.LOLUserIdConsumer;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LOLCrawelParallelService {


    // todo threads and single design

    @Autowired
    LOLUserInfoService userInfoService;

    @Autowired
    LOLUserInfoDao userInfoDao;

    public void consumeUserId(){
        LOLUserIdPool pool = LOLUserIdPool.getInstance();
        while (true){
            String userId = pool.getUserId();
            LOLUserInfoDTO userInfoDTO = userInfoService.getUserInfoDTOByUserId(userId);
            List<LOLUserEntity> lolUserEntities = userInfoService.transformDTOToEntity(Lists.newArrayList(userInfoDTO));
            userInfoDao.insertUserInfoList(lolUserEntities);
        }
    }
    public void produceGameId(){
        LOLUserIdPool pool = LOLUserIdPool.getInstance();
        while (true){
            pool.getUserId();
        }
    }


    public void threadRun(){

        // todo threads start
//        Thread userIdConsumer = new Thread(() -> {
//            int count = 0;
//            while(count<100){
//                System.out.println("userIdConsumer: " + count++);
//            }
//        });
//
//        Thread gameIdConsumer = new Thread(() -> {
//            int count = 0;
//            while (count<100){
//                System.out.println("gameIdConsumer: " + count++);
//            }
//        });

//        userIdConsumer.start();
//        try {
//            userIdConsumer.sleep(1000*10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        gameIdConsumer.start();

        Thread userIdConsumer = new Thread(new LOLUserIdConsumer());
        Thread gameIdConsumer = new Thread(new LOLGameIdConsumer());

        System.out.println("-------------outsize start-----------");
        userIdConsumer.start();
        gameIdConsumer.start();
        System.out.println("-------------outsize end-----------");

    }


}
