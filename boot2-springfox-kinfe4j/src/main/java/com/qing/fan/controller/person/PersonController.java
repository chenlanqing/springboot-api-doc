package com.qing.fan.controller.person;

import com.qing.fan.common.entity.Result;
import com.qing.fan.entity.UserListQuery;
import com.qing.fan.entity.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author QingFan
 * @version 1.0.0
 * @date 2024年08月17日 22:50
 */
@RestController
@Api(tags = "用户接口")
@RequestMapping("/v1/person")
public class PersonController {

    @GetMapping("/get-person")
    @ApiOperation("根据用户id获取用户信息")
    public Result<UserVO> getPerson(@RequestParam("id") Long id) {
        return Result.ok(new UserVO());
    }

    @PostMapping("/list-person")
    @ApiOperation("查询用户列表")
    public Result<List<UserVO>> listUser(@RequestBody UserListQuery query) {
        return Result.ok(new ArrayList<>());
    }
}
