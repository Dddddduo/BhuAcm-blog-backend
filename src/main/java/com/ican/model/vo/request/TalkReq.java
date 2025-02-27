package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 说说Request
 *
 * @author Dduo
 */
@Data
@ApiModel(description = "说说Request")
public class TalkReq {

    /**
     * 说说id
     */
    @ApiModelProperty(value = "说说id")
    private Integer id;

    /**
     * 说说内容
     */
    @NotBlank(message = "说说内容不能为空")
    @ApiModelProperty(value = "说说内容", required = true)
    private String talkContent;

    /**
     * 说说图片
     */
    @ApiModelProperty(value = "说说图片")
    private String images;

    /**
     * 是否置顶 (0否 1是)
     */
    @NotNull(message = "置顶状态不能为空")
    @ApiModelProperty(value = "是否置顶 (0否 1是)", required = true)
    private Integer isTop;

    /**
     * 说说状态 (1公开 2私密)
     */
    @NotNull(message = "说说状态不能为空")
    @ApiModelProperty(value = "说说状态 (1公开 2私密)", required = true)
    private Integer status;

}