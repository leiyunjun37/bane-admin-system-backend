package com.example.demo.controller;

import com.example.demo.entity.Pets;
import com.example.demo.service.IOperationLogService;
import com.example.demo.service.IPetsService;
import com.example.demo.utils.*;
import com.example.demo.utils.RequestBody.Pets.PetsInsertObject;
import com.example.demo.utils.RequestBody.Pets.PetsUpdateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetsController {

    @Autowired
    private IPetsService petsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IOperationLogService operationLogService;

    public static String pageInfo = "宠物信息";

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size,
                                         @RequestParam("age") Integer age,
                                         @RequestParam("petname") String petname,
                                         @RequestParam("variety") String variety,
                                         @RequestParam("owner") String owner,
                                         HttpServletRequest request) {
        try {
            Integer start = (page - 1) * size + 1;
            List<Pets> pets = petsService.selectPets(start, size, petname, variety, owner, age);
            Integer total = petsService.countPets();
            PageDataResult<Object> result = PageDataResultUtils.success(pets, total);
            result.setMessage("select success");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            String type = "查询";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            operationLogService.insert(username, type, pageInfo, formattedDateTime);
            return result;
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }

    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody PetsUpdateObject requestBody, HttpServletRequest request) {
        try {
            String petname = requestBody.getPetname();
            String owner = requestBody.getOwner();
            String variety = requestBody.getVariety();
            if (petsService.checkPetnameOwnerUnique(owner, petname, variety)) {
                Integer id = requestBody.getId();
                Integer age = requestBody.getAge();
                petsService.update(id, petname, owner, age, variety);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("update success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "编辑";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("this owner already have the same pet");
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
            petsService.delete(id);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("delete success");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            String type = "删除";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            operationLogService.insert(username, type, pageInfo, formattedDateTime);
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody PetsInsertObject requestBody, HttpServletRequest request) {
        try {
            String petname = requestBody.getPetname();
            String owner = requestBody.getOwner();
            String variety = requestBody.getVariety();
            if (petsService.checkPetnameOwnerUnique(owner, petname, variety)) {
                Integer age = requestBody.getAge();
                petsService.insertPets(petname, owner, variety, age);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "新增";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("insert failed, this owner already hava this pet");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
