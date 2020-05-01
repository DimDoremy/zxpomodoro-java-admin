package com.zxpomodoro.miniprogram.mapper;

import com.zxpomodoro.miniprogram.model.HandBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("SELECT * FROM handBook")
    List<HandBook> selectAllUserBook();
}
