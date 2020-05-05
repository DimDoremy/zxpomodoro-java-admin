package com.zxpomodoro.miniprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxpomodoro.miniprogram.mapper.UserMapper;
import com.zxpomodoro.miniprogram.model.UserData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Api("userData表操作接口，用于用户基本信息访问")
@RestController
public class UserController {
    @Resource
    private UserMapper userMapper;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * 用于测试使用的全部访问url
     * TODO:日后需要隐藏url
     *
     * @return 所访问的数据库全部信息
     */
    @ApiOperation(value = "获取全部用户基本信息", notes = "用于测试的Api，请不要在正式开发中使用")
    @RequestMapping(value = "/api/alluserdata", method = RequestMethod.GET)
    public String allUserData() {
        List<UserData> userDataList = userMapper.selectAllUserData();
        return userDataList.toString();
    }

    /**
     * 获取单个用户信息
     *
     * @param jsonId 查询用户的openid
     * @return 用户信息封装后的json字符串
     */
    @ApiOperation(value = "获取单个用户信息")
    @ApiImplicitParam(name = "jsonId",
            value = "单openid的json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/querybyopenid", method = RequestMethod.POST)
    public String selectByOpenid(@RequestBody String jsonId) {
        try {
            var maps = mapper.readValue(jsonId, Map.class);
            UserData userData = userMapper.selectByOpenid((String) maps.get("openid"));
            log.info(userData.getOpenid() + ": select success");
            return mapper.writeValueAsString(userData);
        } catch (Exception e) {
            return "{\"error\":" + e.getMessage() + "}";
        }
    }

    /**
     * 更新单个用户信息
     *
     * @param jsonData 需要更新的json字符串数据
     * @return 是否更新成功
     */
    @ApiOperation(value = "更新单个用户信息")
    @ApiImplicitParam(name = "jsonData",
            value = "更新后用户数据json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/updateuserdata", method = RequestMethod.POST)
    public boolean updateUserData(@RequestBody String jsonData) {
        try {
            UserData userData = mapper.readValue(jsonData, UserData.class);
            userMapper.updateUserData(userData);
            log.info(userData.getOpenid() + ": update success");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 注册用户添加新信息
     *
     * @param jsonData 新注册用户信息
     * @return 成功返回1，失败-1
     */
    @ApiOperation(value = "注册新用户信息")
    @ApiImplicitParam(name = "jsonData",
            value = "新注册用户数据json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/insertuserdata", method = RequestMethod.POST)
    public int insertUserData(@RequestBody String jsonData) {
        try {
            UserData userData = mapper.readValue(jsonData, UserData.class);
            log.info(userData.getOpenid() + ": insert success");
            return userMapper.insertUserData(userData);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    /**
     * 删除用户数据库指定条目
     *
     * @param jsonId 单openid的json字符串请求
     * @return 成功返回1，失败-1
     */
    @ApiOperation(value = "删除用户数据库指定条目")
    @ApiImplicitParam(name = "jsonId",value = "单openid的json字符串请求",dataType = "String",paramType = "query")
    @PostMapping(value = "/api/deleteuserdata")
    public int deleteByOpenid(@RequestBody String jsonId) {
        try {
            var maps = mapper.readValue(jsonId, Map.class);
            var userData = userMapper.selectByOpenid((String) maps.get("openid"));
            int returns = userMapper.deleteByOpenid((String) maps.get("openid"));
            log.info(userData.getOpenid() + ": delete success");
            return returns;
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
