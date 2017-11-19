package com.dade.crawel.gamecrawel.test;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

@Mapper
public interface TestDao {

    @Select("select * from lol_user")
    public Map<String,Object> find();

    @Insert("insert into lol_user(user_id,user_name) "+
            "values(#{userId},#{userName})")
    public void insert(@Param("userId")String userId,@Param("userName")String userName);

}
