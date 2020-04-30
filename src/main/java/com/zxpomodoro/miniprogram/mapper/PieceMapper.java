package com.zxpomodoro.miniprogram.mapper;

import com.zxpomodoro.miniprogram.model.UserPiece;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PieceMapper {
    @Select("SELECT * FROM userPiece")
    List<UserPiece> selectAllUserPiece();

    @Select("SELECT * FROM userPiece WHERE openid=#{openid}")
    UserPiece selectByOpenid(String openid);

    @Update("UPDATE userPiece SET " +
            "xuanyuanjian=${xuanyuanjian}, " +
            "donghuangzhong=${donghuangzhong}, " +
            "pangufu=${pangufu}, " +
            "lianyaohu=${lianyaohu}, " +
            "haotianta=${haotianta}, " +
            "fuxiqin=${fuxiqin}, " +
            "shennongding=${shennongding}, " +
            "kongtongyin=${kongtongyin}, " +
            "kunlunjing=${kunlunjing}, " +
            "nvwashi=${nvwashi} " +
            "WHERE openid=#{openid}")
    void updateUserPiece(UserPiece userPiece);

    @Insert("INSERT INTO userPiece " +
            "VALUES (#{openid},${xuanyuanjian}," +
            "${donghuangzhong},${pangufu}," +
            "${lianyaohu},${haotianta}," +
            "${fuxiqin},${shennongding}," +
            "${kongtongyin},${kunlunjing},${nvwashi})")
    int insertNewUserPiece(UserPiece userPiece);
}
