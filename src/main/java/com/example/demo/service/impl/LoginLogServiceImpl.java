package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.LoginLog;
import com.example.demo.mapper.LoginLogMapper;
import com.example.demo.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insert(String username, String logintime) {
        LoginLog operationLog = new LoginLog();
        operationLog.setUsername(username);
        operationLog.setLogintime(logintime);
        operationLog.setIs_delete(0);
        loginLogMapper.insert(operationLog);
    }
}
