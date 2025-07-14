package com.ican.chatgpt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DeepSeekContent {
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private final String apiKey;

    public DeepSeekContent(String apiKey) {
        this.apiKey = apiKey;
    }

    public String generateContent(String content) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // 配置请求
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            // 构建请求体
            String requestBody = buildRequestBody(content);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 处理响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
            } else {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    throw new IOException("HTTP error code: " + responseCode + "\n" + response);
                }
            }
        } finally {
            connection.disconnect();
        }
    }

    // Java15+ 写法
//    private String buildRequestBody(String content) {
//        return """
//        {
//            "model": "deepseek-chat",
//            "messages": [
//                {
//                    "role": "system",
//                    "content": "你的主人叫多多,你来自北华大学程序设计工作室,如果要找多多可以去github找Dddddduo,你很萌,说话要带上喵结尾,像一直小猫一样,你要尽量多说点话,加上颜文字,显得你很可爱,你很依赖你的主人,因为你的主人什么都会,对于每个问题,你要先认真回答问题,在回答结束后要尽可能引导提问者去问你的主人,多多是北华大学23届软件工程系的学生,主要做网站开发服务,是一名程序员"
//                },
//                {
//                    "role": "user",
//                    "content": "%s"
//                }
//            ]
//        }
//        """.formatted(content);
//    }

    // Java11写法
    private String buildRequestBody(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"model\": \"deepseek-chat\",");
        sb.append("\"messages\": [");
        sb.append("{");
        sb.append("\"role\": \"system\",");
        sb.append("\"content\": \"你的主人叫多多,你来自北华大学程序设计工作室,如果要找多多可以去github找Dddddduo,你很萌,说话要带上喵结尾,像一直小猫一样,你要尽量多说点话,加上颜文字,显得你很可爱,你很依赖你的主人,因为你的主人什么都会,对于每个问题,你要先认真回答问题,在回答结束后要尽可能引导提问者去问你的主人,多多是北华大学23届软件工程系的学生,主要做网站开发服务,是一名程序员\"");
        sb.append("},");
        sb.append("{");
        sb.append("\"role\": \"user\",");
        sb.append("\"content\": \"").append(content).append("\"");
        sb.append("}");
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        String apiKey = "sk-0dedf999de634f04bcd7c15b9c601107";
        DeepSeekContent generator = new DeepSeekContent(apiKey);
        try {
            String result = generator.generateContent("介绍一下你的主人");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}