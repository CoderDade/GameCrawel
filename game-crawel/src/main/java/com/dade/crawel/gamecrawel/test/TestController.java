package com.dade.crawel.gamecrawel.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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




}
