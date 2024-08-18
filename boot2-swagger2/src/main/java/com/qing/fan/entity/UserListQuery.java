package com.qing.fan.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author QingFan
 * @version 1.0.0
 * @date 2024年08月17日 23:00
 */
@Data
public class UserListQuery {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("用户名，模糊搜索")
    private String username;
    @ApiModelProperty("邮箱地址，精确搜索")
    private String email;
}
