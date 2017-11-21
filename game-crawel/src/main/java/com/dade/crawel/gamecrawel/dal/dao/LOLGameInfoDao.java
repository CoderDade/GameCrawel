package com.dade.crawel.gamecrawel.dal.dao;

import com.dade.crawel.gamecrawel.dal.entity.LOLGameInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface LOLGameInfoDao {

    @Select("<script>" +
            "select user_id from lol_game_info where 1=1 " +
            "<foreach collection='userIds' index='index' item='item' open=' and game_id not in(' " +
            "separator=',' close=')' >" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    List<String> queryUserIds(@Param("userIds")Set<String> userIds);

    @Insert("<script>" +
            "insert into lol_game_info(seconds, team, x, y, killer, victim, assistings) " +
            "<foreach collection='gameInfos' index='index' item='item' open=' value ' separator=',' close=''>" +
            "(#{item.seconds},#{item.team},#{item.x},#{item.y},#{item.killer}," +
            "#{item.victim},#{item.assistings})" +
            "</foreach>" +
            "</script>")
    void insertGameInfo(@Param("gameInfos") List<LOLGameInfoEntity> gameInfos);

}
