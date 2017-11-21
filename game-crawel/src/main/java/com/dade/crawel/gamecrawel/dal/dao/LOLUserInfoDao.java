package com.dade.crawel.gamecrawel.dal.dao;

import com.dade.crawel.gamecrawel.dal.entity.LOLUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LOLUserInfoDao {

    @Insert("<script>" +
            "insert into lol_user(user_id, name, area, icon, lasted, tier, rank) " +
            "<foreach collection='userInfos' index='index' item='item' open=' value ' separator=',' close=''>" +
            "(#{item.userId},#{item.name},#{item.area},#{item.icon},#{item.lasted}," +
            "#{item.tier},#{item.rank})" +
            "</foreach>" +
            "</script>")
    void insertUserInfoList(@Param("userInfos") List<LOLUserEntity> userInfos);

}
