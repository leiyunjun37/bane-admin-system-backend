package com.example.demo.utils;


import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;


@Component
public class LoggerUtils {

    public void ExceptionLog(Logger logger, HttpServletRequest request, Exception e) {
        MDC.put("requestUrl", request.getRequestURL().toString());
        MDC.put("requestMethod", request.getMethod());
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException error) {
            throw new RuntimeException(error);
        }
        MDC.put("requestBody", requestBody.toString());
        logger.error("请求异常 - URL: {}, Method: {}, Body: {}", MDC.get("requestUrl"), MDC.get("requestMethod"), MDC.get("requestBody"), e);
        MDC.remove("requestUrl");
        MDC.remove("requestMethod");
        MDC.remove("requestBody");
    }
}
