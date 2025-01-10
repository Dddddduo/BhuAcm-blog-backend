package com.ican.model.dto;

import lombok.Data;

/**
 * QQ token信息
 *
 * @author Dduo
 * @date 2023/04/06 23:31
 **/
@Data
public class QqTokenDTO {

    /**
     * openid
     */
    private String openid;

    /**
     * 客户端id
     */
    private String client_id;
}