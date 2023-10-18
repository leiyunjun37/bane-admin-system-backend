package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Pets;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IPetsService extends IService<Pets> {

    Boolean insertPets(String petname, String owner, String variety, Integer age);

    List<Pets> selectPets(Integer start, Integer size, String petname, String variety, String owner, Integer age);

    void update(Integer id, String petname, String owner, Integer age, String variety);

    Boolean checkPetnameOwnerUnique(String owner, String petname, String variety);

    void delete(Integer id);

    Integer countPets();

    void deleteThroughGuest(String owner);

    void changeOwner(Integer ownerId, String newOwnerName);

}
