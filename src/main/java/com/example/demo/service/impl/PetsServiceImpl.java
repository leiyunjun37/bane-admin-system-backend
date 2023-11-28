package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Pets;
import com.example.demo.entity.VipGuests;
import com.example.demo.mapper.PetsMapper;
import com.example.demo.mapper.VipGuestsMapper;
import com.example.demo.service.IPetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PetsServiceImpl extends ServiceImpl<PetsMapper, Pets> implements IPetsService {

    @Autowired
    private PetsMapper petsMapper;

    @Autowired
    private VipGuestsMapper vipGuestsMapper;

    @Override
    public Boolean insertPets(String petname, String owner, String variety, Integer age, String create_time) {
        Pets pets = new Pets();
        pets.setOwner(owner);
        pets.setPetname(petname);
        pets.setAge(age);
        pets.setIs_delete(0);
        pets.setVariety(variety);
        pets.setCreate_time(create_time);
        pets.setUpdate_time("-");
        petsMapper.insert(pets);
        return true;
    }

    @Override
    public HashMap<String, Object> selectPets(String petname, String variety, String owner) {
        HashMap<String, Object> hashMap = new HashMap<>();
        QueryWrapper<Pets> wrapper = new QueryWrapper<>();
        wrapper.like("petname", petname);
        wrapper.like("variety", variety);
        wrapper.eq("is_delete", 0);
        wrapper.orderByDesc("create_time");
        if (owner.isEmpty()) {
            List<Pets> pets = petsMapper.selectList(wrapper);
            Integer total = petsMapper.selectCount(wrapper);
            hashMap.put("data", pets);
            hashMap.put("total", total);
            return hashMap;
        } else {
            wrapper.eq("owner", owner);
            List<Pets> pets = petsMapper.selectList(wrapper);
            Integer total = petsMapper.selectCount(wrapper);
            hashMap.put("data", pets);
            hashMap.put("total", total);
            return hashMap;
        }
    }

    @Override
    public void update(Integer id, String petname, String owner, Integer age, String variety, String update_time) {
        Pets pets = new Pets();
        pets.setId(id);
        pets.setPetname(petname);
        pets.setAge(age);
        pets.setOwner(owner);
        pets.setVariety(variety);
        pets.setUpdate_time(update_time);
        petsMapper.updateById(pets);
    }

    @Override
    public Boolean checkPetnameOwnerUnique(String owner, String petname, Integer id) {
        QueryWrapper<Pets> wrapper = new QueryWrapper<>();
        wrapper.eq("owner", owner)
                .eq("is_delete", 0);
        List<Pets> pets = petsMapper.selectList(wrapper);
        QueryWrapper<Pets> wrapper1 = new QueryWrapper<>();
        wrapper.eq("id", id);
        Pets oldPet = petsMapper.selectOne(wrapper);
        String oldPetname = oldPet.getPetname();
        if (petname.equals(oldPetname)) {
            return true;
        } else {
            boolean result = true;
            for (Pets pet : pets) {
                if (pet.getPetname().equals(petname)) {
                    result = false;
                    break;
                }
            }
            return result;
        }
    }

    @Override
    public Boolean checkInsertPetnameOwnerUnique(String owner, String petname) {
        QueryWrapper<Pets> wrapper = new QueryWrapper<>();
        wrapper.eq("owner", owner)
                .eq("is_delete", 0);
        List<Pets> pets = petsMapper.selectList(wrapper);
        boolean result = true;
        for (Pets pet : pets) {
            if (pet.getPetname().equals(petname)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public void delete(Integer id) {
        Pets pets = new Pets();
        pets.setId(id);
        pets.setIs_delete(1);
        petsMapper.updateById(pets);
    }

    @Override
    public Integer countPets() {
        QueryWrapper<Pets> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return petsMapper.selectCount(wrapper);
    }

    @Override
    public void deleteThroughGuest(String owner) {
        Pets pets = new Pets();
        pets.setIs_delete(1);
        QueryWrapper<Pets> wrapper = new QueryWrapper<>();
        wrapper.eq("owner", owner);
        petsMapper.update(pets, wrapper);
    }

    @Override
    public void changeOwner(Integer ownerId, String newOwnerName) {
        QueryWrapper<VipGuests> wrapper = new QueryWrapper<>();
        wrapper.eq("id", ownerId);
        VipGuests vipGuests = vipGuestsMapper.selectOne(wrapper);
        String oldOwnerName = vipGuests.getName();
        Pets pets = new Pets();
        pets.setOwner(newOwnerName);
        QueryWrapper<Pets> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("owner", oldOwnerName);
        wrapper1.eq("is_delete", 0);
        petsMapper.update(pets, wrapper1);
    }

    @Override
    public List<Pets> getAllPets() {
        QueryWrapper<Pets> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return petsMapper.selectList(wrapper);
    }
}
