package com.dade.crawel.gamecrawel.pool;

import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

public class LOLUserIdPool {

//    private Set<String> userIdPool = Sets.newConcurrentHashSet();
//    private Set<String> gameIdPool = Sets.newConcurrentHashSet();
//
    private static LOLUserIdPool instance = new LOLUserIdPool();
    private LOLUserIdPool(){}

    public static LOLUserIdPool getInstance(){
        return instance;
    }
//
//    public String getUserId(){
//        if (CollectionUtils.isEmpty(userIdPool)){
//
//        }
//    }

    private int queueSize = 1000*1000;
    private ArrayBlockingQueue<String> userIdQueue = new ArrayBlockingQueue<String>(queueSize);
    private ArrayBlockingQueue<String> userGameIdQueue = new ArrayBlockingQueue<String>(queueSize);

    public String getUserId(){
        String userId = null;
        try {
            userId = userIdQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public String getUserGameId(){
        String userGameId = null;
        try {
            userGameId = userGameIdQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userGameId;
    }

    // todo change single userID TO userIds
    public void setUserId(String userId){
        try {
            userIdQueue.put(userId);
            userGameIdQueue.put(userId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setUserIds(Set<String> userIds){
        userIds.forEach(userId->{
            try {
                userIdQueue.put(userId);
                userGameIdQueue.put(userId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }













}
