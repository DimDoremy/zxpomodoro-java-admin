package com.zxpomodoro.miniprogram.mapper;

import com.zxpomodoro.miniprogram.model.UserData;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用于读取数据库userData表的接口
 */
@Mapper
public interface UserMapper {
    /**
     * 查询全部UserData的信息
     *
     * @return 获取的全部UserData行信息
     */
    @Select("SELECT * FROM userData")
    List<UserData> selectAllUserData();

    /**
     * 用openid查询单一用户的信息
     *
     * @param openid 微信用户的openid
     * @return 单一用户信息的封装类
     */
    @Select("SELECT * FROM userData WHERE openid=#{openid}")
    UserData selectByOpenid(String openid);

    /**
     * 更新用户数据
     *
     * @param userData 更新数据读取的用户数据类
     */
    @Update("UPDATE userData SET " +
            "nickname=#{nickname}, " +
            "avatarurl=#{avatarurl}, " +
            "time=${time}, " +
            "soul=${soul}, " +
            "lifepoint=${lifepoint}, " +
            "experience=${experience} " +
            "WHERE openid=#{openid}")
    void updateUserData(UserData userData);

    /**
     * 添加新用户数据
     * @param userData 添加的用户数据
     * @return ？
     */
    @Insert("INSERT INTO userData " +
            "VALUES(#{openid},#{nickname},#{avatarurl},${time},${soul},${lifepoint},${experience})")
    @Options(keyProperty = "openid", keyColumn = "openid", useGeneratedKeys = true)
    int insertUserData(UserData userData);

    @Delete("DELETE FROM userData WHERE openid=#{openid}")
    int deleteByOpenid(String openid);
}
