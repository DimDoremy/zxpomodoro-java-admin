package com.zxpomodoro.miniprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxpomodoro.miniprogram.mapper.PieceMapper;
import com.zxpomodoro.miniprogram.model.UserPiece;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Api("userPiece表操作接口，用于用户物品列表访问")
@RestController
public class PieceController {
    @Resource
    private PieceMapper pieceMapper;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * 用于测试使用的全部访问url
     * TODO:日后需要隐藏url
     *
     * @return 所访问的数据库全部信息
     */
    @ApiOperation(value = "获取全部用户物品列表", notes = "用于测试的Api，请不要在正式开发中使用")
    @RequestMapping(value = "/api/alluserpiece", method = RequestMethod.GET)
    public String allUserPiece() {
        List<UserPiece> userPieceList = pieceMapper.selectAllUserPiece();
        return userPieceList.toString();
    }

    /**
     * 获取单个用户背包列表信息
     *
     * @param jsonId 单openid的json字符串请求
     * @return 用户背包信息封装后的json字符串
     */
    @ApiOperation(value = "获取单个用户背包列表信息")
    @ApiImplicitParam(name = "jsonId",
            value = "单openid的json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/piecebyopenid", method = RequestMethod.POST)
    public String selectByOpenid(@RequestBody String jsonId) {
        try {
            var maps = mapper.readValue(jsonId, Map.class);
            var userPiece = pieceMapper.selectByOpenid((String) maps.get("openid"));
            log.info(userPiece.getOpenid() + ": select success");
            return mapper.writeValueAsString(userPiece);
        } catch (Exception e) {
            return "{\"error\":" + e.getMessage() + "}";
        }
    }

    /**
     * 更新单个用户背包信息
     *
     * @param jsonData 更新后用户数据json字符串请求
     * @return 是否更新成功
     */
    @ApiOperation(value = "更新单个用户背包信息")
    @ApiImplicitParam(name = "jsonData",
            value = "更新后用户数据json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/updateuserpiece", method = RequestMethod.POST)
    public boolean updateUserData(@RequestBody String jsonData) {
        try {
            UserPiece userPiece = mapper.readValue(jsonData, UserPiece.class);
            pieceMapper.updateUserPiece(userPiece);
            log.info(userPiece.getOpenid() + ": update success");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 注册用户新建背包新信息
     *
     * @param jsonData 新注册用户信息
     * @return 成功返回1，失败-1
     */
    @ApiOperation(value = "注册新用户新建背包信息")
    @ApiImplicitParam(name = "jsonData",
            value = "新注册用户数据json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/insertuserpiece", method = RequestMethod.POST)
    public int insertUserData(@RequestBody String jsonData) {
        try {
            UserPiece userPiece = mapper.readValue(jsonData, UserPiece.class);
            log.info(userPiece.getOpenid() + ": insert success");
            return pieceMapper.insertNewUserPiece(userPiece);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
