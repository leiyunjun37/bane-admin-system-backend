package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.OperationLog;

import java.util.HashMap;
import java.util.List;

public interface IOperationLogService extends IService<OperationLog> {

    void insert(String username, String type, String page, String datetime);

    HashMap<String, Object> select(String username, String pageInfo, String type, String begintime, String endtime);

    Integer countOperationLog();

    List<OperationLog> getAllOperationLogs();
}
