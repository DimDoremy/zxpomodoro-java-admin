package com.zxpomodoro.miniprogram.mapper;

import com.zxpomodoro.miniprogram.model.RecruitPublic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecruitMapper {
    @Select("SELECT * FROM recruitPublic")
    List<RecruitPublic> selectAllRecruit();

    @Select("SELECT * FROM recruitPublic WHERE mission=#{mission}")
    RecruitPublic selectByMission(String mission);

    @Update("UPDATE recruitPublic SET " +
            "isopen=${isopen}, " +
            "isspecial=${isspecial}, " +
            "rank=${rank}, " +
            "title=#{title}, " +
            "description=#{description} " +
            "WHERE mission=#{mission}")
    void updateRecruit(RecruitPublic recruitPublica);

    @Insert("INSERT INTO recruitPublic " +
            "VALUES(#{mission},${isopen},${isspecial},${rank},#{title},#{description})")
    @Options(keyProperty = "mission", keyColumn = "mission", useGeneratedKeys = true)
    int insertRecruit(RecruitPublic recruitPublic);

    @Delete("DELETE FROM userData WHERE mission=#{mission}")
    int deleteByMission(String mission);
}
