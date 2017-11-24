package com.dade.crawel.gamecrawel.controller;

import com.dade.crawel.gamecrawel.pool.LOLUserIdPool;
import com.dade.crawel.gamecrawel.service.LOLCrawelParallelService;
import com.dade.crawel.gamecrawel.threads.LOLUserIdConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lol")
public class LOLCrawelController {

    @Autowired
    LOLCrawelParallelService crawelParallelService;

    @RequestMapping("consumer")
    public void consumer(){
        Thread consumer = new Thread(new LOLUserIdConsumer());
        consumer.start();
    }

    @RequestMapping("producer")
    public void producer(@RequestParam String userId){
        LOLUserIdPool pool = LOLUserIdPool.getInstance();
        pool.setUserId(userId);
    }

}
