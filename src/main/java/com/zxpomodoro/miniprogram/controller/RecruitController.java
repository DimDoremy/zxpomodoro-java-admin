package com.zxpomodoro.miniprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxpomodoro.miniprogram.mapper.BlockMapper;
import com.zxpomodoro.miniprogram.mapper.ListMapper;
import com.zxpomodoro.miniprogram.model.RecruitBlock;
import com.zxpomodoro.miniprogram.model.RecruitList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(value = "招募板数据库操作")
public class RecruitController {
    ObjectMapper mapper = new ObjectMapper();
    @Resource
    private BlockMapper blockMapper;
    @Resource
    private ListMapper listMapper;

    /**
     * 用于测试使用的全部访问url
     * TODO:日后需要隐藏url
     *
     * @return 所访问的数据库全部信息
     */
    @ApiOperation(value = "获取全部招募板信息", notes = "用于测试的Api，请不要在正式开发中使用")
    @GetMapping(value = "/api/allrecruit")
    public String allRecruitBlock() {
        List<RecruitBlock> recruitBlockList = blockMapper.selectAllRecruitBlock();
        List<RecruitList> recruitLists = listMapper.selectAllRecruitList();
        return recruitBlockList.toString() + "\n" + recruitLists.toString();
    }

    /**
     * 获取单个招募板信息
     *
     * @param jsonId 查询用户的openid
     * @return 用户信息封装后的json字符串
     */
    @ApiOperation(value = "获取单个招募板信息")
    @ApiImplicitParam(name = "jsonId",
            value = "单openid的json字符串请求",
            dataType = "String",
            paramType = "query")
    @PostMapping(value = "/api/recruitbymakerid")
    public String selectByOpenid(@RequestBody String jsonId) {
        try {
            var maps = mapper.readValue(jsonId, Map.class);
            RecruitBlock recruitBlock = blockMapper.selectByOpenid((String) maps.get("makerid"));
            RecruitList recruitList = listMapper.selectByOpenid((String) maps.get("makerid"));
            String jsonData = "{\"makerid\":" + recruitBlock.getMakerid()
                    + ",\"bossid\":" + recruitBlock.getBossid()
                    + ",\"bosslife\":" + recruitBlock.getBosslife()
                    + ",\"gamer\":" + "{\"gamer1\":" + recruitList.getGamer1() + ",\"gamer2\":" + recruitList.getGamer2() + ",\"gamer3\":" + recruitList.getGamer3() + "}" + "}";
            System.out.println(jsonData);
            log.info(recruitBlock.getMakerid() + ": select success");
            return jsonData;
        } catch (Exception e) {
            return "{\"error\":" + e.getMessage() + "}";
        }
    }

    /**
     * 更新单个招募板信息
     *
     * @param jsonData 需要更新的json字符串数据
     * @return 是否更新成功
     */
    @ApiOperation(value = "更新单个招募板信息")
    @ApiImplicitParam(name = "jsonData",
            value = "更新后招募板数据json字符串请求",
            dataType = "String",
            paramType = "query")
    @PostMapping(value = "/api/updaterecruit")
    public boolean updateRecruitBlock(@RequestBody String jsonData) {
        try {
            var recruitMap = mapper.readValue(jsonData, Map.class);
            var gamers = recruitMap.get("gamer");
            String gamerStr = mapper.writeValueAsString(gamers);
            var gamerMap = mapper.readValue(gamerStr, Map.class);
            RecruitBlock recruitBlock = new RecruitBlock((String) recruitMap.get("makerid"), (int) recruitMap.get("bossid"), (int) recruitMap.get("bosslife"));
            RecruitList recruitList = new RecruitList((String) recruitMap.get("makerid"), (String) gamerMap.get("gamer1"), (String) gamerMap.get("gamer2"), (String) gamerMap.get("gamer3"));
            blockMapper.updateRecruitBlock(recruitBlock);
            listMapper.updateRecruitList(recruitList);
            log.info(recruitBlock.getMakerid() + ": update success");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 新建招募板信息
     *
     * @param jsonData 新建招募板信息
     * @return 成功返回1，失败-1
     */
    @ApiOperation(value = "新建招募板信息")
    @ApiImplicitParam(name = "jsonData",
            value = "招募板数据json字符串请求",
            dataType = "String",
            paramType = "query")
    @PostMapping(value = "/api/insertrecruit")
    public int insertRecruitBlock(@RequestBody String jsonData) {
        try {
            var recruitMap = mapper.readValue(jsonData, Map.class);
            var gamers = recruitMap.get("gamer");
            String gamerStr = mapper.writeValueAsString(gamers);
            var gamerMap = mapper.readValue(gamerStr, Map.class);
            RecruitBlock recruitBlock = new RecruitBlock((String) recruitMap.get("makerid"), (int) recruitMap.get("bossid"), (int) recruitMap.get("bosslife"));
            RecruitList recruitList = new RecruitList((String) recruitMap.get("makerid"), (String) gamerMap.get("gamer1"), (String) gamerMap.get("gamer2"), (String) gamerMap.get("gamer3"));
            int returns = blockMapper.insertNewRecruitBlock(recruitBlock) + listMapper.insertNewRecruitList(recruitList);
            log.info(recruitBlock.getMakerid() + ": insert success");
            return returns;
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
    @ApiImplicitParam(name = "jsonId", value = "单openid的json字符串请求", dataType = "String", paramType = "query")
    @PostMapping(value = "/api/deleterecruit")
    public int deleteByOpenid(@RequestBody String jsonId) {
        try {
            var maps = mapper.readValue(jsonId, Map.class);
            var recruitBlock = blockMapper.selectByOpenid((String) maps.get("makerid"));
            int returns = blockMapper.deleteByOpenid((String) maps.get("makerid")) + listMapper.deleteByOpenid((String) maps.get("makerid"));
            log.info(recruitBlock.getMakerid() + ": delete success");
            return returns;
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
