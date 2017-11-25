package com.dade.crawel.gamecrawel.util;

import java.util.Collection;

public class SOUTUtil {

    public static void DBStart(){
        System.out.println("----------insert into db start----------");
    }

    public static void DBEnd(){
        System.out.println("----------insert into db end----------");
    }

    public static void Collection(Collection collection){
        collection.forEach(System.out::println);
    }

    public static void DBInsert(Collection collection){
        DBStart();
        Collection(collection);
        DBEnd();
    }

}
