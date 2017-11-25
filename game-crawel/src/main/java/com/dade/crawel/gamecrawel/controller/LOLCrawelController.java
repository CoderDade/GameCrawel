package com.dade.crawel.gamecrawel.controller;

import com.dade.crawel.gamecrawel.pool.LOLGameIdPool;
import com.dade.crawel.gamecrawel.pool.LOLUserIdPool;
import com.dade.crawel.gamecrawel.service.LOLCrawelParallelService;
import com.dade.crawel.gamecrawel.threads.LOLGameIdConsumer;
import com.dade.crawel.gamecrawel.threads.LOLGameIdProducer;
import com.dade.crawel.gamecrawel.threads.LOLUserIdConsumer;
import com.dade.crawel.gamecrawel.threads.LOLUserIdConsumerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("lol")
public class LOLCrawelController {

    @Autowired
    LOLCrawelParallelService crawelParallelService;

    @Autowired
    LOLUserIdConsumer lolUserIdConsumer;

    @Autowired
    LOLGameIdConsumer lolGameIdConsumer;

    @SuppressWarnings("all")
    @Autowired
    LOLGameIdProducer lolGameIdProducer;

    @RequestMapping("query_gameId_pool")
    public String queryGameIdPool(){
        LOLGameIdPool gameIdPool = LOLGameIdPool.getInstance();
        return gameIdPool.getGameId();
    }

    @RequestMapping("start_gameId_producer")
    public void startGameIdProducer(){
        Thread gameIdProducer = new Thread(lolGameIdProducer);
        gameIdProducer.start();

        String userId = "2934835788";
        LOLUserIdPool userIdPool = LOLUserIdPool.getInstance();
        userIdPool.setUserId(userId);

    }

    @RequestMapping("consumer")
    public void consumer(){
//        Thread consumer = new Thread(lolUserIdConsumer);
        Thread consumer = new Thread(lolGameIdConsumer);
        consumer.start();
    }

    @RequestMapping("producer")
    public void producer(@RequestParam String userId){
//        LOLUserIdPool pool = LOLUserIdPool.getInstance();
    LOLGameIdPool pool = LOLGameIdPool.getInstance();
        pool.setGameId(userId);
}

    @Autowired
    LOLUserIdConsumerTest lolUserIdConsumerTest;

    @RequestMapping("service_test")
    public void serviceTest(){
        String userId = "4121478980";
        try {
            lolUserIdConsumerTest.consumeUserIdForTest(userId);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @RequestMapping("userId_consumer")
    public String userIdConsumer(){
        Thread consumer = new Thread(lolUserIdConsumer);
        consumer.start();
        return "Thread start, thread name is " + consumer.getName();
    }

    @RequestMapping("gameId_consumer")
    public String gameIdConsumer(){
        Thread consumer = new Thread(lolGameIdConsumer);
        consumer.start();
        return "Thread start, thread name is " + consumer.getName();
    }

    @RequestMapping("thread_start")
    public void threadStart(){
        Thread userIdConsumer = new Thread(lolUserIdConsumer);
        userIdConsumer.start();
        Thread gameIdConsumer = new Thread(lolGameIdConsumer);
        gameIdConsumer.start();
        Thread gameIdProducer = new Thread(lolGameIdProducer);
        gameIdProducer.start();

        String userId = "2934835788";
        LOLUserIdPool userIdPool = LOLUserIdPool.getInstance();
        userIdPool.setUserId(userId);

    }


}
