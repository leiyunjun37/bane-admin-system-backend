package com.example.demo.controller;

import com.example.demo.entity.VipGuests;
import com.example.demo.service.IOperationLogService;
import com.example.demo.service.IOrderRecordService;
import com.example.demo.service.IPetsService;
import com.example.demo.service.IVipGuestsService;
import com.example.demo.utils.*;
import com.example.demo.utils.RequestBody.Vipguests.VipguestsUpdateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.utils.RequestBody.Vipguests.VipguestInsertObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(value = "http://localhost:8080")
@RestController
@RequestMapping("/vipguests")
public class VipGuestsController {

    @Autowired
    private IVipGuestsService vipGuestsService;

    @Autowired
    private IPetsService petsService;

    @Autowired
    private IOrderRecordService orderRecordService;

    @Autowired
    private IOperationLogService operationLogService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static String pageInfo = "会员信息";

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("page") Integer page,
                                         @RequestParam("name") String name,
                                         @RequestParam("begintime") String begintime,
                                         @RequestParam("endtime") String endtime,
                                         @RequestParam("size") Integer size) {
        try {
            if (size == -1) {
                List<VipGuests> vipGuests = vipGuestsService.getAllVipGuests();
                Integer total = vipGuestsService.countVipGuest();
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", vipGuests);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            } else {
                Integer start = (page - 1) * size;
                Integer end = start + size;
                HashMap<String, Object> hashMap = vipGuestsService.getVipGuests(name, begintime, endtime);
                List<VipGuests> data = (List<VipGuests>) hashMap.get("data");
                Integer total = (Integer) hashMap.get("total");
                List<VipGuests> vipGuests;
                if (total < end) {
                    vipGuests = data.subList(start, total);
                } else {
                    vipGuests = data.subList(start, end);
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", vipGuests);
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
    public PageNoneDataResult<Object> insert(@RequestBody VipguestInsertObject requestBody, HttpServletRequest request) {
        try {
            String name = requestBody.getName();
            String nameCol = "name";
            String conway = requestBody.getConway();
            String conwayCol = "conway";
            if (vipGuestsService.checkInsertUnqiue(nameCol, name) && vipGuestsService.checkInsertUnqiue(conwayCol, conway)) {
                String petname = requestBody.getPets();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String registertime = now.format(formatter);
                petsService.insertPets(petname, name, "-", 1, registertime);
                Integer balance = requestBody.getBalance();
                vipGuestsService.insertVipGuests(name, registertime, conway, balance);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "新增";
                operationLogService.insert(username, type, pageInfo, registertime);
                operationLogService.insert(username, type, "宠物信息", registertime);
                String comment = String.format("于%s充值%d元", registertime, balance);
                orderRecordService.recharge(name, registertime, comment, balance);
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("the name or conway already exist");
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
            String owner = vipGuestsService.updateIsDelete(id);
            petsService.deleteThroughGuest(owner);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("delete success");
            String token = request.getHeader("Authorization").substring("Bearer ".length());
            String username = jwtTokenUtil.getUsernameFromToken(token);
            String type = "删除";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            operationLogService.insert(username, type, pageInfo, formattedDateTime);
            operationLogService.insert(username, type, "宠物信息", formattedDateTime);
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody VipguestsUpdateObject requestBody, HttpServletRequest request) {
        try {
            String name = requestBody.getName();
            String nameCol = "name";
            String conway = requestBody.getConway();
            String conwayCol = "conway";
            Integer id = requestBody.getId();
            if (vipGuestsService.checkUnqiue(nameCol, name, id) && vipGuestsService.checkUnqiue(conwayCol, conway, id)) {
                vipGuestsService.update(id, conway, name);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("update success");
                petsService.changeOwner(id, name);
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "编辑";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
                operationLogService.insert(username, type, "宠物信息", formattedDateTime);
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("the name or conway already exist");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
