package com.zxpomodoro.miniprogram.mapper;

import com.zxpomodoro.miniprogram.model.HandBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("SELECT * FROM handBook")
    List<HandBook> selectAllUserBook();

    @Select("SELECT * FROM handBook WHERE openid=#{openid}")
    HandBook selectByOpenid(String openid);

    @Update("UPDATE handBook SET " +
            "monster1=${monster1}, " +
            "monster2=${monster2}, " +
            "monster3=${monster3}, " +
            "monster4=${monster4}, " +
            "monster5=${monster5}, " +
            "monster6=${monster6}, " +
            "monster7=${monster7} " +
            "WHERE openid=#{openid}")
    void updateHandBook(HandBook handBook);

    @Insert("INSERT INTO handBook " +
            "VALUES (#{openid},${monster1},${monster2},${monster3},${monster4},${monster5},${monster6},${monster7}")
    int insertNewUserHandBook(HandBook handBook);
}
