package com.dade.crawel.gamecrawel.threads;

public class LOLGameIdConsumer implements Runnable {
    @Override
    public void run() {
//        testRun();
    }

    private void testRun(){
        int count = 0;
        while (count<100){
            System.out.println("gameIdConsumer: " + count++);
        }
    }
}
