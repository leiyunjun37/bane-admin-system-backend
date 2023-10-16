package com.example.demo.controller;


import com.example.demo.entity.PetServe;
import com.example.demo.service.IPetServeService;
import com.example.demo.utils.PageDataResult;
import com.example.demo.utils.PageDataResultUtils;
import com.example.demo.utils.PageNoneDataResult;
import com.example.demo.utils.PageNoneDataResultUtils;
import com.example.demo.utils.RequestBody.PetServe.PetServeInsertObject;
import com.example.demo.utils.RequestBody.PetServe.PetServeUpdateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petserve")
public class PetServeController {

    @Autowired
    private IPetServeService petServeService;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("serveName") String serveName,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) {
        try {
            Integer start = (page - 1) * size;
            List<PetServe> petServes = petServeService.select(serveName, start, size);
            PageDataResult<Object> result = PageDataResultUtils.success(petServes);
            result.setMessage("select success");
            return result;
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody PetServeInsertObject requestBody) {
        try {
            String serveName = requestBody.getServeName();
            if (petServeService.checkServeNameUnique(serveName)) {
                String unit = requestBody.getUnit();
                Integer price = requestBody.getPrice();
                String comment = requestBody.getComment();
                petServeService.insert(serveName, comment, price, unit);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("insert success");
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
    public PageNoneDataResult<Object> delete(@RequestParam("id") Integer id) {
        try {
            petServeService.delete(id);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("delete success");
            return result;
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody PetServeUpdateObject requestBody) {
        try {
            String serveName = requestBody.getServeName();

            if (petServeService.checkServeNameUnique(serveName)) {
                Integer id = requestBody.getId();
                String comment = requestBody.getComment();
                Integer price = requestBody.getPrice();
                String unit = requestBody.getUnit();
                petServeService.update(id, serveName, price, unit, comment);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("update success");
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
