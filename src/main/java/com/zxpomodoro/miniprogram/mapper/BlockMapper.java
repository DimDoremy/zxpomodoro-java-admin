package com.zxpomodoro.miniprogram.mapper;

import com.zxpomodoro.miniprogram.model.RecruitBlock;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlockMapper {
    @Select("SELECT * FROM recruitBlock")
    List<RecruitBlock> selectAllRecruitBlock();

    @Select("SELECT * FROM recruitBlock WHERE makerid=#{makerid}")
    RecruitBlock selectByOpenid(String makerid);

    @Update("UPDATE recruitBlock SET " +
            "bossid=${bossid}, " +
            "bosslife=${bosslife} " +
            "WHERE makerid=#{makerid}")
    void updateRecruitBlock(RecruitBlock recruitBlock);

    @Insert("INSERT INTO recruitBlock " +
            "VALUES (#{makerid},${bossid},${bosslife})")
    int insertNewRecruitBlock(RecruitBlock recruitBlock);

    @Delete("DELETE FROM recruitBlock WHERE makerid=#{makerid}")
    int deleteByOpenid(String makerid);
}
