package com.example.demo.controller;

import com.example.demo.entity.Pets;
import com.example.demo.service.IPetsService;
import com.example.demo.utils.PageDataResult;
import com.example.demo.utils.PageDataResultUtils;
import com.example.demo.utils.PageNoneDataResult;
import com.example.demo.utils.PageNoneDataResultUtils;
import com.example.demo.utils.RequestBody.Pets.PetsInsertObject;
import com.example.demo.utils.RequestBody.Pets.PetsUpdateObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetsController {

    @Autowired
    private IPetsService petsService;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size,
                                         @RequestParam("age") Integer age,
                                         @RequestParam("petname") String petname,
                                         @RequestParam("variety") String variety,
                                         @RequestParam("owner") String owner) {
        Integer start = (page - 1) * size + 1;
        List<Pets> pets = petsService.selectPets(start, size, petname, variety, owner, age);
        PageDataResult<Object> result = PageDataResultUtils.success(pets);
        result.setMessage("select success");
        return result;
    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody PetsUpdateObject requestBody) {
        String petname = requestBody.getPetname();
        String owner = requestBody.getOwner();
        String variety = requestBody.getVariety();
        if (petsService.checkPetnameOwnerUnique(owner, petname, variety)) {
            Integer id = requestBody.getId();
            Integer age = requestBody.getAge();
            petsService.update(id, petname, owner, age, variety);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("update success");
            return result;
        } else {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage("this owner already have the same pet");
            return result;
        }
    }

    @DeleteMapping("/delete")
    public PageNoneDataResult<Object> delete(@RequestParam("id") Integer id) {
        petsService.delete(id);
        PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
        result.setMessage("delete success");
        return result;
    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody PetsInsertObject requestBody) {
        String petname = requestBody.getPetname();
        String owner = requestBody.getOwner();
        String variety = requestBody.getVariety();
        if (petsService.checkPetnameOwnerUnique(owner, petname, variety)) {
            Integer age = requestBody.getAge();
            petsService.insertPets(petname, owner, variety, age);
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
            result.setMessage("insert success");
            return result;
        } else {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage("insert failed, this owner already hava this pet");
            return result;
        }
    }
}
