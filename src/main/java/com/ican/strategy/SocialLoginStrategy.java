package com.ican.strategy;

import com.ican.model.vo.request.CodeReq;

/**
 * 第三方登录策略
 *
 * @author Dduo
 */
public interface SocialLoginStrategy {

    /**
     * 登录
     *
     * @param data 第三方code
     * @return {@link String} Token
     */
    String login(CodeReq data);
}
