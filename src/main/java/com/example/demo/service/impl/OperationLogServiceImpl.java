package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.OperationLog;
import com.example.demo.mapper.OperationLogMapper;
import com.example.demo.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public HashMap<String, Object> select(String username, String pageInfo, String type, String begintime, String endtime, Integer start, Integer size) {
        HashMap<String, Object> hashMap = new HashMap<>();
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        wrapper.like("type", type);
        wrapper.between("datetime", begintime, endtime);
        wrapper.eq("is_delete", 0);
        wrapper.like("page", pageInfo);
        Page<OperationLog> page = new Page<>(start, size);
        List<OperationLog> operationLogs = operationLogMapper.selectPage(page,wrapper).getRecords();
        Integer total = operationLogMapper.selectCount(wrapper);
        hashMap.put("data", operationLogs);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    public Integer countOperationLog() {
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return operationLogMapper.selectCount(wrapper);
    }

    @Override
    public List<OperationLog> getAllOperationLogs() {
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return operationLogMapper.selectList(wrapper);
    }
}
