package com.qing.fan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author QingFan
 * @version 1.0.0
 * @date 2024年08月17日 22:53
 */
@Data
@ApiModel("用户实体")
public class UserVO {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("国家码")
    private String nationCode;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
}
