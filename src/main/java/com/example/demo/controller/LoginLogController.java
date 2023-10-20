package com.example.demo.controller;

import com.example.demo.entity.LoginLog;
import com.example.demo.service.ILoginLogService;
import com.example.demo.utils.PageDataResult;
import com.example.demo.utils.PageDataResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(value = "http://localhost:8080")
@RestController
@RequestMapping("/loginlog")
public class LoginLogController {

    @Autowired
    private ILoginLogService loginLogService;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("username") String username,
                                         @RequestParam("begintime") String begintime,
                                         @RequestParam("endtime") String endtime,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) {
        try {
            if (size == -1) {
                List<LoginLog> loginLogs = loginLogService.getAllLoginLogs();
                Integer total = loginLogService.countLoginLog();
                PageDataResult<Object> result = PageDataResultUtils.success(loginLogs, total);
                result.setMessage("select success");
                return result;
            } else {
                Integer start = ( page - 1) * size;
                HashMap<String, Object> hashMap = loginLogService.select(username, begintime, endtime, start, size);
                Integer total = (Integer) hashMap.get("total");
                List<LoginLog> loginLogs = (List<LoginLog>) hashMap.get("data");
                PageDataResult<Object> result = PageDataResultUtils.success(loginLogs, total);
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
