package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PetServe;

import java.util.HashMap;
import java.util.List;

public interface IPetServeService extends IService<PetServe> {

    HashMap<String, Object> select(String serveName);

    Boolean checkServeNameUnique(String serveName, Integer id);

    Boolean checkInsertServeNameUnique(String serveName);

    void insert(String serveName, String comment, Integer price, String create_time);

    void delete(Integer id);

    void update(Integer id, String serveName, Integer price, String unit, String comment);

    Integer countPetServe();

    List<PetServe> getAllPetServes();

}
