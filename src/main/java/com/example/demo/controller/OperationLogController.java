package com.example.demo.controller;

import com.example.demo.entity.OperationLog;
import com.example.demo.service.IOperationLogService;
import com.example.demo.utils.PageDataResult;
import com.example.demo.utils.PageDataResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(value = "http://localhost:8080")
@RestController
@RequestMapping("/operationlog")
public class OperationLogController {

    @Autowired
    private IOperationLogService operationLogService;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("username") String username,
                                       @RequestParam("pageInfo") String pageInfo,
                                       @RequestParam("type") String type,
                                       @RequestParam("begintime") String begintime,
                                       @RequestParam("endtime") String endtime,
                                       @RequestParam("page") Integer page,
                                       @RequestParam("size") Integer size) {
        try {
            if (size == -1) {
                List<OperationLog> operationLogs = operationLogService.getAllOperationLogs();
                Integer total = operationLogService.countOperationLog();
                PageDataResult<Object> result = PageDataResultUtils.success(operationLogs, total);
                result.setMessage("select success");
                return result;
            } else {
                Integer start = ( page - 1) * size;
                HashMap<String, Object> hashMap = operationLogService.select(username, pageInfo, type, begintime, endtime, start, size);
                Integer total = (Integer) hashMap.get("total");
                List<OperationLog> operationLogs = (List<OperationLog>) hashMap.get("data");
                PageDataResult<Object> result = PageDataResultUtils.success(operationLogs, total);
                result.setMessage("select success");
                return result;
            }
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
