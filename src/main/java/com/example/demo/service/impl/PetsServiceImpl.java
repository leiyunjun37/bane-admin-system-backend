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

import java.util.List;

@Service
public class PetsServiceImpl extends ServiceImpl<PetsMapper, Pets> implements IPetsService {

    @Autowired
    private PetsMapper petsMapper;

    @Autowired
    private VipGuestsMapper vipGuestsMapper;

    @Override
    public Boolean insertPets(String petname, String owner, String variety, Integer age) {
        Pets pets = new Pets();
        pets.setOwner(owner);
        pets.setPetname(petname);
        pets.setAge(age);
        pets.setIs_delete(0);
        pets.setVariety(variety);
        petsMapper.insert(pets);
        return true;
    }

    @Override
    public List<Pets> selectPets(Integer start, Integer size, String petname, String variety, String owner, Integer age) {
        QueryWrapper<Pets> wrapper = new QueryWrapper<>();
        wrapper.like("petname", petname);
        wrapper.like("variety", variety);
        wrapper.like("owner", owner);
        wrapper.gt("age", age);
        wrapper.eq("is_delete", 0);
        Page<Pets> page = new Page<>(start, size);
        return petsMapper.selectPage(page, wrapper).getRecords();
    }

    @Override
    public void update(Integer id, String petname, String owner, Integer age, String variety) {
        Pets pets = new Pets();
        pets.setId(id);
        pets.setPetname(petname);
        pets.setAge(age);
        pets.setOwner(owner);
        pets.setVariety(variety);
        petsMapper.updateById(pets);
    }

    @Override
    public Boolean checkPetnameOwnerUnique(String owner, String petname, String variety) {
        QueryWrapper<Pets> wrapper = new QueryWrapper<>();
        wrapper.eq("owner", owner)
                .eq("petname", petname)
                .eq("variety", variety)
                .eq("is_delete", 0);
        Pets exist = petsMapper.selectOne(wrapper);
        return exist == null;
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
}
