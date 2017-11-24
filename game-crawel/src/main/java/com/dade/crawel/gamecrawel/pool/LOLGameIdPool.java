package com.dade.crawel.gamecrawel.pool;

import java.util.concurrent.ArrayBlockingQueue;

public class LOLGameIdPool {

    private static LOLGameIdPool instance = new LOLGameIdPool();
    private LOLGameIdPool(){}

    public static LOLGameIdPool getInstance(){
        return instance;
    }

    private int queueSize = 1000;
    private ArrayBlockingQueue<String> gameIdQueue = new ArrayBlockingQueue<String>(queueSize);


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
