package com.dade.crawel.gamecrawel.test;

import com.dade.crawel.gamecrawel.dal.dao.LOLGameInfoDao;
import com.dade.crawel.gamecrawel.dal.entity.LOLGameInfoEntity;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public String test(){
        return "Hello Game Crawel";
    }

    @Autowired
    TestDao testDao;

    @RequestMapping("dao/find")
    public Map<String, Object> testDao(){
        Map<String, Object> res = testDao.find();
        return res;
    }

    @RequestMapping("dao/insert")
    public void testDaoInsert(@RequestParam("userId")String userId,
                              @RequestParam("userName")String userName){
        testDao.insert(userId, userName);
    }

    @Autowired
    LOLGameInfoDao lolGameInfoDao;

    @RequestMapping("game_info/query")
    public List<LOLGameInfoEntity> testGameInfo(){
        return lolGameInfoDao.queryGameInfoList();
    }

    @RequestMapping(value = "game_info/insert", method = RequestMethod.POST)
    public void testInsertGameInfo(@RequestBody LOLGameInfoEntity info){
        lolGameInfoDao.insertGameInfoEntity(info);
    }

    @Autowired
    TestService testService;

    @RequestMapping("service")
    public String testServiceForCall(){
        String sercie = testService.testSercie();
        return sercie;
    }


}
