package com.zxpomodoro.miniprogram.mapper;

import com.zxpomodoro.miniprogram.model.RecruitList;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ListMapper {
    @Select("SELECT * FROM recruitList")
    List<RecruitList> selectAllRecruitList();

    @Select("SELECT * FROM recruitList WHERE makerid=#{makerId}")
    RecruitList selectByOpenid(String makerId);

    @Update("UPDATE recruitList SET " +
            "gamer1=#{gamer1}, " +
            "gamer2=#{gamer2}, " +
            "gamer3=#{gamer3} " +
            "WHERE makerid=#{makerid}")
    void updateRecruitList(RecruitList recruitList);

    @Insert("INSERT INTO recruitList " +
            "VALUES (#{makerid},#{gamer1},#{gamer2},#{gamer3})")
    int insertNewRecruitList(RecruitList recruitList);

    @Delete("DELETE FROM recruitList WHERE makerid=#{makerId}")
    int deleteByOpenid(String makerId);
}
