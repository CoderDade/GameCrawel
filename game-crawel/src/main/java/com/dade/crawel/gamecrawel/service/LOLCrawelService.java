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
import org.springframework.util.CollectionUtils;

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
        Set<String> duplicateIds = gameContextDao.queryGameIdByGameIds(gameIds);
        gameIds = Sets.difference(gameIds, duplicateIds);
        gameContextDao.insertGameContexts(lolGameContexts);
        Set<String> userIds = Sets.newHashSet();
        for (String gameId : gameIds) {
            LOLGameInfoDTO gameInfoDTOS = gameInfoService.getUserIdByGameId(gameId);
            List<LOLGameInfoEntity> lolGameInfos = gameInfoService.transformDTOToEntity(gameInfoDTOS);
            Set<String> killers = lolGameInfos.stream().map(LOLGameInfoEntity::getKiller).collect(Collectors.toSet());
            Set<String> victims = lolGameInfos.stream().map(LOLGameInfoEntity::getVictim).collect(Collectors.toSet());
            Set<String> assistings = lolGameInfos.stream()
                    .map(LOLGameInfoEntity::getAssistings)
                    .flatMap(list->Lists.newArrayList(list.split("\\,")).stream())
                    .collect(Collectors.toSet());
            Set<String> allUser = Sets.union(Sets.union(killers, victims), assistings);
            Set<String> useedIds = userInfoDao.queryUserIdsUsed(allUser);
            allUser = Sets.difference(allUser, useedIds);
            userIds.addAll(allUser);
            if (!CollectionUtils.isEmpty(lolGameInfos))
                lolGameInfoDao.insertGameInfo(lolGameInfos);

        }

        while(!CollectionUtils.isEmpty(userIds)){

            List<LOLUserInfoDTO> userInfos = userIds.stream().map(userId -> userInfoService.getUserInfoDTOByUserId(userId)).collect(Collectors.toList());
            List<LOLUserEntity> users = userInfoService.transformDTOToEntity(userInfos);
            userInfoDao.insertUserInfoList(users);

            List<LOLGameContextEntity> gameContextEntityList = userIds
                    .stream()
                    .flatMap(userId -> {
                        LOLGameContextDTO dto = gameContextService.getGameContextDTOByUserId(userId);
                        return gameContextService.transformDTOToEntity(dto).stream();
                    })
                    .collect(Collectors.toList());

            Set<String> tmpGameIds = gameContextEntityList.stream().map(LOLGameContextEntity::getGameId).collect(Collectors.toSet());
            Set<String> tmpDuplicateIds = gameContextDao.queryGameIdByGameIds(tmpGameIds);
            tmpGameIds = Sets.difference(tmpGameIds, tmpDuplicateIds);
            gameContextDao.insertGameContexts(gameContextEntityList);
            Set<String> tmpUsers = Sets.newHashSet();
            tmpGameIds.stream().forEach(gameId->{
                LOLGameInfoDTO gameInfoDTOS = gameInfoService.getUserIdByGameId(gameId);
                List<LOLGameInfoEntity> lolGameInfos = gameInfoService.transformDTOToEntity(gameInfoDTOS);
                Set<String> killers = lolGameInfos.stream().map(LOLGameInfoEntity::getKiller).collect(Collectors.toSet());
                Set<String> victims = lolGameInfos.stream().map(LOLGameInfoEntity::getVictim).collect(Collectors.toSet());
                Set<String> assistings = lolGameInfos.stream()
                        .map(LOLGameInfoEntity::getAssistings)
                        .flatMap(list->Lists.newArrayList(list.split("\\,")).stream())
                        .collect(Collectors.toSet());
                Set<String> allUser = Sets.union(Sets.union(killers, victims), assistings);
                Set<String> useedIds = userInfoDao.queryUserIdsUsed(allUser);
                allUser = Sets.difference(allUser, useedIds);
                tmpUsers.addAll(allUser);
                if (!CollectionUtils.isEmpty(lolGameInfos))
                    lolGameInfoDao.insertGameInfo(lolGameInfos);
            });
            userIds = tmpUsers;
        }


    }

}
