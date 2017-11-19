package com.dade.crawel.gamecrawel.dal.dao;

import com.dade.crawel.gamecrawel.dal.entity.LOLGameInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LOLGameInfoDao {

    @Select("<script>" +
            "select user_id userId, game_id gameId from lol_game_info " +
            "</script>")
    List<LOLGameInfoEntity> queryGameInfoList();

    @Insert("<script>" +
            "insert into lol_game_info(user_id, game_id)  value(#{info.userId},#{info.gameId})" +
            "</script>")
    void insertGameInfoEntity(@Param("info") LOLGameInfoEntity info);

}
