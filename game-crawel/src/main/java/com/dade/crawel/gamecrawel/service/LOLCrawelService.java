package com.dade.crawel.gamecrawel.service;

import com.dade.crawel.gamecrawel.dal.dao.LOLGameContextDao;
import com.dade.crawel.gamecrawel.dal.dao.LOLGameInfoDao;
import com.dade.crawel.gamecrawel.dal.dao.LOLUserInfoDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLGameContextEntity;
import com.dade.crawel.gamecrawel.dal.entity.LOLGameInfoEntity;
import com.dade.crawel.gamecrawel.dal.entity.LOLUserEntity;
import com.dade.crawel.gamecrawel.dto.LOLGameContextDTO;
import com.dade.crawel.gamecrawel.dto.LOLGameInfoDTO;
import com.dade.crawel.gamecrawel.dto.LOLUserInfoDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LOLCrawelService {

    @Autowired
    LOLUserInfoService userInfoService;
    @Autowired
    LOLGameContextService gameContextService;
    @Autowired
    LOLGameInfoService gameInfoService;

    @Autowired
    LOLUserInfoDao userInfoDao;
    @Autowired
    LOLGameContextDao gameContextDao;
    @Autowired
    LOLGameInfoDao lolGameInfoDao;


    public void crawel(){

        String startUserId = "4112595947";
        LOLUserInfoDTO userInfoDTO = userInfoService.getUserInfoDTOByUserId(startUserId);
        List<LOLUserEntity> lolUsers = userInfoService.transformDTOToEntity(Lists.newArrayList(userInfoDTO));
        userInfoDao.insertUserInfoList(lolUsers);

        LOLGameContextDTO gameContextDTO = gameContextService.getGameContextDTOByUserId(startUserId);
        List<LOLGameContextEntity> lolGameContexts = gameContextService.transformDTOToEntity(gameContextDTO);

        Set<String> gameIds = lolGameContexts.stream().map(LOLGameContextEntity::getGameId).collect(Collectors.toSet());
        gameIds = gameContextDao.queryGameIdByGameIds(gameIds);
        gameContextDao.insertGameContexts(lolGameContexts);
        gameIds.stream().forEach(gameId->{
            LOLGameInfoDTO gameInfoDTOS = gameInfoService.getUserIdByGameId(gameId);
            List<LOLGameInfoEntity> lolGameInfos = gameInfoService.transformDTOToEntity(gameInfoDTOS);
            Set<String> killers = lolGameInfos.stream().map(LOLGameInfoEntity::getKiller).collect(Collectors.toSet());
            Set<String> victims = lolGameInfos.stream().map(LOLGameInfoEntity::getVictim).collect(Collectors.toSet());
            Set<String> assistings = lolGameInfos.stream()
                    .map(LOLGameInfoEntity::getAssistings)
                    .flatMap(list->Lists.newArrayList(list.split("\\,")).stream())
                    .collect(Collectors.toSet());
            Set<String> userIds = Sets.newHashSet();
            userIds.addAll(killers);
            userIds.addAll(victims);
            userIds.addAll(assistings);


            lolGameInfoDao.insertGameInfo(lolGameInfos);
        });





    }

}
