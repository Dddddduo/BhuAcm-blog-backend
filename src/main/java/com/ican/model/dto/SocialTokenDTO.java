package com.ican.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 第三方token
 *
 * @author Dduo
 */
@Data
@Builder
@ApiModel(description = "第三方token")
public class SocialTokenDTO {

    /**
     * 开放id
     */
    @ApiModelProperty(value = "开放id")
    private String openId;

    /**
     * 访问令牌
     */
    @ApiModelProperty(value = "访问令牌")
    private String accessToken;

    /**
     * 登录类型
     */
    @ApiModelProperty(value = "登录类型")
    private Integer loginType;

}