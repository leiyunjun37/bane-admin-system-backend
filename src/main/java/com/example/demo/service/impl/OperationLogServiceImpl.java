package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.OperationLog;
import com.example.demo.mapper.OperationLogMapper;
import com.example.demo.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void insert(String username, String type, String page, String datetime) {
        OperationLog operationLog = new OperationLog();
        operationLog.setUsername(username);
        operationLog.setType(type);
        operationLog.setDatetime(datetime);
        operationLog.setPage(page);
        operationLog.setIs_delete(0);
        operationLogMapper.insert(operationLog);
    }
}
