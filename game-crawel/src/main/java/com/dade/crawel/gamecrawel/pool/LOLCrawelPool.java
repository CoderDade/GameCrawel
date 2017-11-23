package com.dade.crawel.gamecrawel.pool;

import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class LOLCrawelPool {

//    private Set<String> userIdPool = Sets.newConcurrentHashSet();
//    private Set<String> gameIdPool = Sets.newConcurrentHashSet();
//
    private static LOLCrawelPool instance = new LOLCrawelPool();
    private LOLCrawelPool(){}

    public static LOLCrawelPool getInstance(){
        return instance;
    }
//
//    public String getUserId(){
//        if (CollectionUtils.isEmpty(userIdPool)){
//
//        }
//    }

    private int queueSize = 1000;
    private ArrayBlockingQueue<String> userIdQueue = new ArrayBlockingQueue<String>(queueSize);
    private ArrayBlockingQueue<String> gameIdQueue = new ArrayBlockingQueue<String>(queueSize);

    public String getUserId(){
        String userId = null;
        try {
            userId = userIdQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userId;
    }

    // todo change single userID TO userIds
    public void setUserId(String userId){
        try {
            userIdQueue.put(userId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getGameId(){
        String gameId = null;
        try {
            gameId = gameIdQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return gameId;
    }

    // todo change single userID TO userIds
    public void setGameId(String userId){
        try {
            gameIdQueue.put(userId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }











}
