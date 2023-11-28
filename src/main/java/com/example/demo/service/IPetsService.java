package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Pets;

import java.util.HashMap;
import java.util.List;

public interface IPetsService extends IService<Pets> {

    Boolean insertPets(String petname, String owner, String variety, Integer age, String create_time);

    HashMap<String, Object> selectPets(String petname, String variety, String owner);

    void update(Integer id, String petname, String owner, Integer age, String variety, String update_time);

    Boolean checkPetnameOwnerUnique(String owner, String petname, Integer id);

    Boolean checkInsertPetnameOwnerUnique(String owner, String petname);

    void delete(Integer id);

    Integer countPets();

    void deleteThroughGuest(String owner);

    void changeOwner(Integer ownerId, String newOwnerName);

    List<Pets> getAllPets();

}
