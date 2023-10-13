package com.example.demo.config;

import com.example.demo.utils.JwtTokenUtil;
import com.example.demo.utils.RequestBody.invalidTokenResult;
import com.example.demo.utils.RequestBody.invalidTokenResultUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private JwtTokenUtil jwtTokenUtil;

    public JwtInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtTokenUtil.validateToken(token)) {
                return true;
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "invalid token");;
                return false;
            }
        } else {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "missing token");
            return false;
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        try {
            response.setStatus(statusCode);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            invalidTokenResult<Object> errorResponse = invalidTokenResultUtils.error(11111, false, message);
            OutputStream outputStream = response.getOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, errorResponse);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
