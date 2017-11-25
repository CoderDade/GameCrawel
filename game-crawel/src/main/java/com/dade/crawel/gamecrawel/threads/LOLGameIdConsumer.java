package com.dade.crawel.gamecrawel.threads;

import com.dade.crawel.gamecrawel.dal.dao.LOLGameInfoDao;
import com.dade.crawel.gamecrawel.dal.dao.LOLUserInfoDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLGameInfoEntity;
import com.dade.crawel.gamecrawel.dto.LOLGameInfoDTO;
import com.dade.crawel.gamecrawel.pool.LOLGameIdPool;
import com.dade.crawel.gamecrawel.pool.LOLUserIdPool;
import com.dade.crawel.gamecrawel.service.LOLGameInfoService;
import com.dade.crawel.gamecrawel.service.LOLUserInfoService;
import com.dade.crawel.gamecrawel.util.SOUTUtil;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class LOLGameIdConsumer implements Runnable {

    @Autowired
    LOLGameInfoService gameInfoService;

    @SuppressWarnings("all")
    @Autowired
    LOLGameInfoDao gameInfoDao;

    @Autowired
    LOLUserInfoService userInfoService;

    @SuppressWarnings("all")
    @Autowired
    LOLUserInfoDao userInfoDao;

    private void consumerGameId(){
        LOLGameIdPool gameIdPool = LOLGameIdPool.getInstance();
        LOLUserIdPool userIdPool = LOLUserIdPool.getInstance();
        while (true){

//            System.out.println("test");

            String gameId = gameIdPool.getGameId();
            LOLGameInfoDTO userIdByGameId = gameInfoService.getUserIdByGameId(gameId);
            List<LOLGameInfoEntity> lolGameInfoEntities = gameInfoService.transformDTOToEntity(userIdByGameId);

//            System.out.println(lolGameInfoEntities);

            Set<String> victims = lolGameInfoEntities.stream().map(LOLGameInfoEntity::getVictim).collect(Collectors.toSet());
            Set<String> killers = lolGameInfoEntities.stream().map(LOLGameInfoEntity::getKiller).collect(Collectors.toSet());
            Set<String> assistings = lolGameInfoEntities.stream().map(LOLGameInfoEntity::getAssistings).filter(Objects::nonNull).collect(Collectors.toSet());
            assistings = assistings.stream()
                    .flatMap(id-> Sets.newHashSet(id.split("\\,")).stream())
                    .collect(Collectors.toSet());
            Set<String> userIds = Sets.union(Sets.union(victims, killers), assistings);
            userIds = userIds.stream()
                    .filter(id->org.apache.commons.lang3.StringUtils.isNotBlank(id))
                    .map(id->id.replace("[","")
                            .replace("]","")
                            .replace(" ",""))
                    .collect(Collectors.toSet());
            Set<String> usedIds = userInfoDao.queryUserIdsUsed(userIds);
            userIds = Sets.difference(userIds, usedIds);
            userIdPool.setUserIds(userIds);
//            try {
            if (!CollectionUtils.isEmpty(lolGameInfoEntities))
                gameInfoDao.insertGameInfo(lolGameInfoEntities);
//            }catch (Exception e){
//                e.printStackTrace();
//            }


            SOUTUtil.DBInsert(lolGameInfoEntities);
//            System.out.println("----------insert into db start----------");
//            lolGameInfoEntities.forEach(System.out::println);
//            System.out.println("----------insert into db end----------");



        }
    }

    @Override
    public void run() {
//        testRun();
//        System.out.println("thread run start");
        consumerGameId();
//        System.out.println("thread run end");
    }

    private void testRun(){
        int count = 0;
        while (count<100){
            System.out.println("gameIdConsumer: " + count++);
        }
    }
}
