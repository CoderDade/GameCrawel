package com.dade.crawel.gamecrawel.threads;

import com.dade.crawel.gamecrawel.dal.dao.LOLGameContextDao;
import com.dade.crawel.gamecrawel.dal.dao.LOLUserInfoDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLGameContextEntity;
import com.dade.crawel.gamecrawel.dal.entity.LOLUserEntity;
import com.dade.crawel.gamecrawel.dto.LOLGameContextDTO;
import com.dade.crawel.gamecrawel.dto.LOLUserInfoDTO;
import com.dade.crawel.gamecrawel.pool.LOLUserIdPool;
import com.dade.crawel.gamecrawel.service.LOLGameContextService;
import com.dade.crawel.gamecrawel.service.LOLUserInfoService;
import com.dade.crawel.gamecrawel.util.SOUTUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LOLUserIdConsumer implements Runnable {

    @Autowired
    LOLUserInfoService userInfoService;

    @SuppressWarnings("all")
    @Autowired
    LOLUserInfoDao userInfoDao;



    @Override
    public void run() {
//        testRun();
        consumeUserId();
//        testPool();
//        consumeUserIdForTest("4121478980");
    }

    private void testRun(){
        int count = 0;
        while(count<100){
            System.out.println("userIdConsumer: " + count++);
        }
    }

    private void testPool(){
        LOLUserIdPool userIdPool = LOLUserIdPool.getInstance();
        int count = 0;
        while (true){
            String userId = userIdPool.getUserId();
            System.out.println("this is the " + count + " time, and userId is " + userId);
            count++;
        }
    }

    @Deprecated
    private void consumeUserIdForTest(String userId){
        LOLUserInfoDTO userInfoDTO = userInfoService.getUserInfoDTOByUserId(userId);
        List<LOLUserEntity> lolUserEntities = userInfoService.transformDTOToEntity(Lists.newArrayList(userInfoDTO));
        userInfoDao.insertUserInfoList(lolUserEntities);
        System.out.println("----------insert into db start----------");
        lolUserEntities.forEach(System.out::println);
        System.out.println("----------insert into db end----------");
    }

    private void consumeUserId(){
        LOLUserIdPool pool = LOLUserIdPool.getInstance();
        while (true){
//            try {
                String userId = pool.getUserId();
                LOLUserInfoDTO userInfoDTO = userInfoService.getUserInfoDTOByUserId(userId);
                List<LOLUserEntity> lolUserEntities = userInfoService.transformDTOToEntity(Lists.newArrayList(userInfoDTO));
                lolUserEntities = lolUserEntities.stream().filter(Objects::nonNull).collect(Collectors.toList());
                userInfoDao.insertUserInfoList(lolUserEntities);
                SOUTUtil.DBInsert(lolUserEntities);
//            }catch (Exception e){
//                e.printStackTrace();
//            }

//            System.out.println("----------insert into db start----------");
//            lolUserEntities.forEach(System.out::println);
//            System.out.println("----------insert into db end----------");
        }
    }


}
