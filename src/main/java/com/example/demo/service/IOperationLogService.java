package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.OperationLog;

public interface IOperationLogService extends IService<OperationLog> {

    void insert(String username, String type, String page, String datetime);
}
