package com.ican.controller;

import com.ican.chatgpt.DeepSeekContent;
import com.ican.exception.ServiceException;
import com.ican.model.vo.Result;
import com.ican.model.vo.request.PhotoReq;
import com.ican.model.vo.response.AlbumBackResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import org.springframework.beans.factory.annotation.Value;

@Api(tags = "gpt模块")
@RestController
public class ChatGptController {

    // 从配置文件中加载 api-key
    @Value("${deepseek.api-key}")
    private String apiKey;

    @ApiOperation(value = "调用deepseek")
    @GetMapping("/deepseek")
    public Result<String> chat( @RequestParam("message") String message) {
        try {
            System.out.println(message);
            DeepSeekContent deepSeekContent = new DeepSeekContent(apiKey);
            return Result.success(deepSeekContent.generateContent(message));
        } catch (Exception e) {
            e.printStackTrace();
            // 使用更规范的异常处理
            throw new ServiceException("AI服务调用失败");
        }
    }
}
