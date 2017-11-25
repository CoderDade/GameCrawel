
package com.dade.crawel.gamecrawel.threads;

import com.dade.crawel.gamecrawel.dal.dao.LOLGameContextDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLGameContextEntity;
import com.dade.crawel.gamecrawel.dto.LOLGameContextDTO;
import com.dade.crawel.gamecrawel.pool.LOLGameIdPool;
import com.dade.crawel.gamecrawel.pool.LOLUserIdPool;
import com.dade.crawel.gamecrawel.service.LOLGameContextService;
import com.dade.crawel.gamecrawel.util.SOUTUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LOLGameIdProducer implements Runnable {

    @Autowired
    LOLGameContextService gameContextService;

    @SuppressWarnings("all")
    @Autowired
    LOLGameContextDao gameContextDao;

    @Override
    public void run() {
        producer();
    }

    private void producer(){
        LOLUserIdPool pool = LOLUserIdPool.getInstance();
        LOLGameIdPool gameIdPool = LOLGameIdPool.getInstance();
        while (true){
            String userId = pool.getUserGameId();
            LOLGameContextDTO gameContextDTO = gameContextService.getGameContextDTOByUserId(userId);
            List<LOLGameContextEntity> lolGameContexts = gameContextService.transformDTOToEntity(gameContextDTO);

            Set<String> gameIds = lolGameContexts.stream().map(LOLGameContextEntity::getGameId).collect(Collectors.toSet());
            Set<String> usedIds = gameContextDao.queryGameIdByGameIds(gameIds);
            gameIds = Sets.difference(gameIds, usedIds);
            gameIdPool.setGameIds(gameIds);
//            LOLGameContextEntity entity = lolGameContexts.stream().findFirst().orElse(null);
//            gameContextDao.insertGameContexts(Lists.newArrayList(entity));
            gameContextDao.insertGameContexts(lolGameContexts);
            SOUTUtil.DBInsert(lolGameContexts);
        }
    }

}
