package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.LoginLog;

public interface ILoginLogService extends IService<LoginLog> {

    void insert(String username, String logintime);
}
