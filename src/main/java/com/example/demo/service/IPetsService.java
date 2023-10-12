package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Pets;

public interface IPetsService extends IService<Pets> {

    Boolean insertPets(String petname, String owner);
}
