package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.PetServe;
import com.example.demo.mapper.PetServeMapper;
import com.example.demo.service.IPetServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServeServiceImpl extends ServiceImpl<PetServeMapper, PetServe> implements IPetServeService {

    @Autowired
    private PetServeMapper petServeMapper;

    @Override
    public List<PetServe> select(String serveName, Integer start, Integer size) {
        QueryWrapper<PetServe> wrapper = new QueryWrapper<>();
        wrapper.like("serveName", serveName);
        wrapper.eq("is_delete", 0);
        Page<PetServe> page = new Page<>(start, size);
        return petServeMapper.selectPage(page, wrapper).getRecords();
    }

    @Override
    public Boolean checkServeNameUnique(String serveName) {
        QueryWrapper<PetServe> wrapper = new QueryWrapper<>();
        wrapper.eq("name", serveName);
        wrapper.eq("is_delete", 0);
        PetServe exist = petServeMapper.selectOne(wrapper);
        return exist == null;
    }

    @Override
    public void insert(String serveName, String comment, Integer price, String unit) {
        PetServe petServe = new PetServe();
        petServe.setComment(comment);
        petServe.setServeName(serveName);
        petServe.setIs_delete(0);
        petServe.setUnit(unit);
        petServe.setPrice(price);
        petServeMapper.insert(petServe);
    }

    @Override
    public void delete(Integer id) {
        PetServe petServe = new PetServe();
        petServe.setId(id);
        petServe.setIs_delete(1);
        petServeMapper.updateById(petServe);
    }

    @Override
    public void update(Integer id, String serveName, Integer price, String unit, String comment) {
        PetServe petServe = new PetServe();
        petServe.setId(id);
        petServe.setComment(comment);
        petServe.setServeName(serveName);
        petServe.setUnit(unit);
        petServe.setPrice(price);
        petServeMapper.updateById(petServe);
    }

    @Override
    public Integer countPetServe() {
        QueryWrapper<PetServe> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return petServeMapper.selectCount(wrapper);
    }
}
