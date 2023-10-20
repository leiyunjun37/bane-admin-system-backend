package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.LoginLog;
import com.example.demo.entity.VipGuests;

import java.util.HashMap;
import java.util.List;

public interface ILoginLogService extends IService<LoginLog> {

    void insert(String username, String logintime);

    HashMap<String, Object> select(String username, String begintime, String endtime, Integer start, Integer size);

    Integer countLoginLog();

    List<LoginLog> getAllLoginLogs();

}
