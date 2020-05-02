package com.zxpomodoro.miniprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxpomodoro.miniprogram.mapper.BookMapper;
import com.zxpomodoro.miniprogram.model.HandBook;
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
@RestController
@Api("handBook表操作接口，用于用户图鉴解锁访问")
public class BookController {
    @Resource
    private BookMapper bookMapper;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * 用于测试使用的全部访问url
     * TODO:日后需要隐藏url
     *
     * @return 所访问的数据库全部信息
     */
    @ApiOperation(value = "获取全部用户图鉴列表", notes = "用于测试的Api，请不要在正式开发中使用")
    @RequestMapping(value = "/api/allhandbook", method = RequestMethod.GET)
    public String allUserPiece() {
        List<HandBook> handBookList = bookMapper.selectAllUserBook();
        return handBookList.toString();
    }

    /**
     * 获取单个用户图鉴列表信息
     *
     * @param jsonId 单openid的json字符串请求
     * @return 用户图鉴信息封装后的json字符串
     */
    @ApiOperation(value = "获取单个用户图鉴列表信息")
    @ApiImplicitParam(name = "jsonId",
            value = "单openid的json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/handbookbyopenid", method = RequestMethod.POST)
    public String selectByOpenid(@RequestBody String jsonId) {
        try {
            var maps = mapper.readValue(jsonId, Map.class);
            var handBook = bookMapper.selectByOpenid((String) maps.get("openid"));
            log.info(handBook.getOpenid() + ": select success");
            return mapper.writeValueAsString(handBook);
        } catch (Exception e) {
            return "{\"error\":" + e.getMessage() + "}";
        }
    }

    /**
     * 更新单个用户图鉴信息
     *
     * @param jsonData 更新后用户数据json字符串请求
     * @return 是否更新成功
     */
    @ApiOperation(value = "更新单个用户图鉴信息")
    @ApiImplicitParam(name = "jsonData",
            value = "更新后用户数据json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/updatehandbook", method = RequestMethod.POST)
    public boolean updateUserData(@RequestBody String jsonData) {
        try {
            HandBook handBook = mapper.readValue(jsonData, HandBook.class);
            bookMapper.updateHandBook(handBook);
            log.info(handBook.getOpenid() + ": update success");
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 注册用户新建图鉴新信息
     *
     * @param jsonData 新注册用户信息
     * @return 成功返回1，失败-1
     */
    @ApiOperation(value = "注册新用户新建图鉴信息")
    @ApiImplicitParam(name = "jsonData",
            value = "新注册用户数据json字符串请求",
            dataType = "String",
            paramType = "query")
    @RequestMapping(value = "/api/inserthandbook", method = RequestMethod.POST)
    public int insertUserData(@RequestBody String jsonData) {
        try {
            HandBook handBook = mapper.readValue(jsonData, HandBook.class);
            log.info(handBook.getOpenid() + ": insert success");
            return bookMapper.insertNewUserHandBook(handBook);
        } catch (Exception e) {
            log.error(e.getMessage());
            return -1;
        }
    }
}
