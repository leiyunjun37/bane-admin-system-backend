package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Pets;
import com.example.demo.mapper.PetsMapper;
import com.example.demo.service.IPetsService;
import org.springframework.beans.factory.annotation.Autowired;

public class PetsServiceImpl extends ServiceImpl<PetsMapper, Pets> implements IPetsService {

    @Autowired
    private PetsMapper petsMapper;

    @Override
    public Boolean insertPets(String petname, String owner) {
        Pets pets = new Pets();
        pets.setOwner(owner);
        pets.setPetname(petname);
        pets.setAge(1);
        pets.setIs_delete(0);
        pets.setVariety("-");
        petsMapper.insert(pets);
        return true;
    }
}
