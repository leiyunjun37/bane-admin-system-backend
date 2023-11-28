package com.example.demo.controller;


import com.example.demo.entity.PetServe;
import com.example.demo.service.IOperationLogService;
import com.example.demo.service.IPetServeService;
import com.example.demo.utils.*;
import com.example.demo.utils.RequestBody.PetServe.PetServeInsertObject;
import com.example.demo.utils.RequestBody.PetServe.PetServeUpdateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(value = "http://localhost:8080")
@RestController
@RequestMapping("/petserve")
public class PetServeController {

    @Autowired
    private IPetServeService petServeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IOperationLogService operationLogService;

    public static String pageInfo = "宠物服务项目";

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("serveName") String serveName,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) {
        try {
            if (size == -1) {
                List<PetServe> petServes = petServeService.getAllPetServes();
                Integer total = petServeService.countPetServe();
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", petServes);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            } else {
                Integer start = (page - 1) * size;
                Integer end = start + size;
                HashMap<String, Object> hashMap = petServeService.select(serveName);
                Integer total = (Integer) hashMap.get("total");
                List<PetServe> data = (List<PetServe>) hashMap.get("data");
                List<PetServe> petServes;
                if (total < size) {
                    petServes = data.subList(start, total);
                } else {
                    petServes = data.subList(start, end);
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", petServes);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            }
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody PetServeInsertObject requestBody, HttpServletRequest request) {
        try {
            String serveName = requestBody.getServeName();
            if (petServeService.checkInsertServeNameUnique(serveName)) {
                Integer price = requestBody.getPrice();
                String comment = requestBody.getComment();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                petServeService.insert(serveName, comment, price, formattedDateTime);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "新增";
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("insert failed, this serve already exist");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @DeleteMapping("/delete")
    public PageNoneDataResult<Object> delete(@RequestParam("id") Integer id, HttpServletRequest request) {
        try {
            petServeService.delete(id);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("delete success");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            String type = "删除";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            operationLogService.insert(username, type, pageInfo, formattedDateTime);
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody PetServeUpdateObject requestBody, HttpServletRequest request) {
        try {
            String serveName = requestBody.getServeName();
            Integer id = requestBody.getId();
            if (petServeService.checkServeNameUnique(serveName, id)) {
                String comment = requestBody.getComment();
                Integer price = requestBody.getPrice();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                petServeService.update(id, serveName, price, formattedDateTime, comment);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("update success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "编辑";
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("update failed, this name alrady exist");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
