package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.ican.model.vo.Result;
import com.ican.model.vo.request.EmailReq;
import com.ican.model.vo.request.UserInfoReq;
import com.ican.model.vo.request.UserReq;
import com.ican.model.vo.response.UserInfoResp;
import com.ican.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息控制器
 *
 * @author Dduo
 **/
@Api(tags = "用户信息模块")
@RestController
public class UserInfoController {

    @Autowired
    private UserService userService;

    /**
     * 获取登录用户信息
     *
     * @return {@link UserInfoResp} 用户信息
     */

    /*
    * @SaCheckLogin注解是一种基于Spring Boot的安全框架——Saber的注解之一。
    * 该注解可以用于控制器中的方法上，用于限制只有登录用户才能访问该方法。
    * 当用户未登录时，访问该方法会自动跳转到登录页面。
    * 在使用该注解时，需要在Saber的配置文件中进行相关配置，包括登录页面的URL、未登录用户访问受限页面的URL等等。
    * */
//    @SaCheckLogin
    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/user/getUserInfo")
    public Result<UserInfoResp> getUserInfo(HttpServletRequest request) {

        // 从请求中获取当前请求的token token名为satoken
        String token1 = request.getHeader("Authorization");
        System.out.println(token1);

        // 判断一下登录状态
        System.out.println("登录状态: "+StpUtil.isLogin());

        // 从sa-token里面获取token
        String token2 = StpUtil.getTokenValue();  // 获取当前线程的 token
        System.out.println("Token: " + token2);  // 打印出 token 以便调试

        return Result.success(userService.getUserInfo());
    }

    /**
     * 修改用户邮箱
     *
     * @param email 邮箱信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改用户邮箱")
    @SaCheckPermission(value = "user:email:update")
    @PutMapping("/user/email")
    public Result<?> updateUserEmail(@Validated @RequestBody EmailReq email) {
        userService.updateUserEmail(email);
        return Result.success();
    }

    /**
     * 修改用户头像
     *
     * @param file 文件
     * @return {@link Result<String>} 头像地址
     */
    @ApiOperation(value = "修改用户头像")
    @SaCheckPermission(value = "user:avatar:update")
    @PostMapping("/user/avatar")
    public Result<String> updateUserAvatar(@RequestParam(value = "file") MultipartFile file) {
        return Result.success(userService.updateUserAvatar(file));
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改用户信息")
    @SaCheckPermission(value = "user:info:update")
    @PutMapping("/user/info")
    public Result<?> updateUserInfo(@Validated @RequestBody UserInfoReq userInfo) {
        userService.updateUserInfo(userInfo);
        return Result.success();
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改用户密码")
    @PutMapping("/user/password")
    public Result<?> updatePassword(@Validated @RequestBody UserReq user) {
        userService.updatePassword(user);
        return Result.success();
    }

}