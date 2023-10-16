package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PetServe;

import java.util.List;

public interface IPetServeService extends IService<PetServe> {

    List<PetServe> select(String serveName, Integer start, Integer size);

    Boolean checkServeNameUnique(String serveName);

    void insert(String serveName, String comment, Integer price, String unit);

    void delete(Integer id);

    void update(Integer id, String serveName, Integer price, String unit, String comment);

}
