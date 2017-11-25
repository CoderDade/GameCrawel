package com.dade.crawel.gamecrawel.threads;

import com.dade.crawel.gamecrawel.dal.dao.LOLUserInfoDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLUserEntity;
import com.dade.crawel.gamecrawel.dto.LOLUserInfoDTO;
import com.dade.crawel.gamecrawel.service.LOLUserInfoService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Deprecated
public class LOLUserIdConsumerTest {

    @Autowired
    LOLUserInfoService userInfoService;
    @Autowired
    LOLUserInfoDao userInfoDao;

    public void consumeUserIdForTest(String userId){
        LOLUserInfoDTO userInfoDTO = userInfoService.getUserInfoDTOByUserId(userId);
        List<LOLUserEntity> lolUserEntities = userInfoService.transformDTOToEntity(Lists.newArrayList(userInfoDTO));
        userInfoDao.insertUserInfoList(lolUserEntities);
        System.out.println("----------insert into db start----------");
        lolUserEntities.forEach(System.out::println);
        System.out.println("----------insert into db end----------");
    }

}
