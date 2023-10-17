package com.example.demo.controller;

import com.example.demo.entity.VipGuests;
import com.example.demo.service.IPetsService;
import com.example.demo.service.IVipGuestsService;
import com.example.demo.utils.PageDataResult;
import com.example.demo.utils.PageDataResultUtils;
import com.example.demo.utils.PageNoneDataResult;
import com.example.demo.utils.PageNoneDataResultUtils;
import com.example.demo.utils.RequestBody.Vipguests.VipguestsUpdateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.utils.RequestBody.Vipguests.VipguestInsertObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/vipguests")
public class VipGuestsController {

    @Autowired
    private IVipGuestsService vipGuestsService;

    @Autowired
    private IPetsService petsService;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("page") Integer page,
                                 @RequestParam("name") String name,
                                 @RequestParam("registertime") String registertime,
                                 @RequestParam("size") Integer size) {
        try {
            Integer start = (page - 1) * size + 1;
            List<VipGuests> vipGuests = vipGuestsService.getVipGuests(name, registertime, start, size);
            Integer total = vipGuestsService.countVipGuest();
            PageDataResult<Object> result = PageDataResultUtils.success(vipGuests, total);
            result.setMessage("select success");
            return result;
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody VipguestInsertObject requestBody) {
        try {
            String name = requestBody.getName();
            String nameCol = "name";
            String conway = requestBody.getConway();
            String conwayCol = "conway";
            if (vipGuestsService.checkUnqiue(nameCol, name) && vipGuestsService.checkUnqiue(conwayCol, conway)) {
                String petnames = requestBody.getPets();
                String[] pets = petnames.split(" ");
                for (String pet : pets) {
                    petsService.insertPets(pet, name, "-", 1);
                }
                Integer balance = requestBody.getBalance();
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String registertime = currentDate.format(formatter);
                vipGuestsService.insertVipGuests(name, registertime, conway, balance);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
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
    public PageNoneDataResult<Object> delete(@RequestParam("id") Integer id) {
        try {
            String owner = vipGuestsService.updateIsDelete(id);
            petsService.deleteThroughGuest(owner);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("delete success");
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody VipguestsUpdateObject requestBody) {
        try {
            String name = requestBody.getName();
            String nameCol = "name";
            String conway = requestBody.getConway();
            String conwayCol = "conway";
            if (vipGuestsService.checkUnqiue(nameCol, name) && vipGuestsService.checkUnqiue(conwayCol, conway)) {
                Integer id = requestBody.getId();
                vipGuestsService.update(id, conway, name);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
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
