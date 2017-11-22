package com.dade.crawel.gamecrawel.dal.dao;

import com.dade.crawel.gamecrawel.dal.entity.LOLGameContextEntity;
import com.dade.crawel.gamecrawel.dto.LOLGameContextDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface LOLGameContextDao {

    @Insert("<script>" +
            "insert into lol_game_context(game_id, platform_id, champion, queue, season, " +
            "timestamp, lane, role)" +
            "<foreach collection='gameContexts' index='index' item='item' open=' value ' " +
            "separator=',' close='' >" +
            "(#{item.gameId},#{item.platformId},#{item.champion},#{item.queue},#{item.season}," +
            "#{item.timestamp},#{item.lane},#{item.role})" +
            "</foreach>" +
            "</script>")
    void insertGameContexts(@Param("gameContexts")List<LOLGameContextEntity> gameContexts);

    @Select("<script>" +
            "select game_id from lol_game_context where 1=1 " +
            "<foreach collection='gameIds' index='index' item='item' open=' and game_id in(' " +
            "separator=',' close=')' >" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    Set<String> queryGameIdByGameIds(@Param("gameIds") Set<String> gameIds);


}
