package com.zxpomodoro.miniprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxpomodoro.miniprogram.mapper.BookMapper;
import com.zxpomodoro.miniprogram.model.HandBook;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    @ApiOperation(value = "获取全部用户物品列表", notes = "用于测试的Api，请不要在正式开发中使用")
    @RequestMapping(value = "/api/allhandbook", method = RequestMethod.GET)
    public String allUserPiece() {
        List<HandBook> handBookList = bookMapper.selectAllUserBook();
        return handBookList.toString();
    }
}
