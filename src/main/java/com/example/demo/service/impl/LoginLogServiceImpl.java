package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.LoginLog;
import com.example.demo.mapper.LoginLogMapper;
import com.example.demo.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public HashMap<String, Object> select(String username, String begintime, String endtime, Integer start, Integer size) {
        HashMap<String, Object> hashMap = new HashMap<>();
        QueryWrapper<LoginLog> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        wrapper.between("logintime", begintime, endtime);
        wrapper.eq("is_delete", 0);
        Page<LoginLog> page = new Page<>(start, size);
        List<LoginLog> loginLogs = loginLogMapper.selectPage(page,wrapper).getRecords();
        Integer total = loginLogMapper.selectCount(wrapper);
        hashMap.put("data", loginLogs);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    public Integer countLoginLog() {
        QueryWrapper<LoginLog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return loginLogMapper.selectCount(wrapper);
    }

    @Override
    public List<LoginLog> getAllLoginLogs() {
        QueryWrapper<LoginLog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return loginLogMapper.selectList(wrapper);
    }
}
