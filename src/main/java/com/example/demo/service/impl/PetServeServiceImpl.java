package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.PetServe;
import com.example.demo.mapper.PetServeMapper;
import com.example.demo.service.IPetServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PetServeServiceImpl extends ServiceImpl<PetServeMapper, PetServe> implements IPetServeService {

    @Autowired
    private PetServeMapper petServeMapper;

    @Override
    public HashMap<String, Object> select(String serveName) {
        HashMap<String, Object> hashMap = new HashMap<>();
        QueryWrapper<PetServe> wrapper = new QueryWrapper<>();
        wrapper.like("serveName", serveName);
        wrapper.eq("is_delete", 0);
        wrapper.orderByDesc("create_time");
        List<PetServe> petServes = petServeMapper.selectList(wrapper);
        Integer total = petServeMapper.selectCount(wrapper);
        hashMap.put("data", petServes);
        hashMap.put("total", total);
        return hashMap;
    }

    @Override
    public Boolean checkServeNameUnique(String serveName, Integer id) {
        QueryWrapper<PetServe> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        List<PetServe> petServes = petServeMapper.selectList(wrapper);
        boolean result = true;
        for(PetServe serve : petServes) {{
            if (serve.getServeName().equals(serveName)) {
                if(!serve.getId().equals(id)) {
                    result = false;
                    break;
                }
            }
        }}
        return result;
    }

    @Override
    public Boolean checkInsertServeNameUnique(String serveName) {
        QueryWrapper<PetServe> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("serveName", serveName);
        PetServe exist = petServeMapper.selectOne(wrapper);;
        return exist == null;
    }

    @Override
    public void insert(String serveName, String comment, Integer price, String create_time) {
        PetServe petServe = new PetServe();
        if (comment.isEmpty()) {
            petServe.setComment("-");
        } else {
            petServe.setComment(comment);
        }
        petServe.setServeName(serveName);
        petServe.setIs_delete(0);
        petServe.setCreate_time(create_time);
        petServe.setUpdate_time("-");
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
    public void update(Integer id, String serveName, Integer price, String update_time, String comment) {
        PetServe petServe = new PetServe();
        petServe.setId(id);
        if (Boolean.TRUE.equals(comment.isEmpty())) {
            petServe.setComment("-");
        } else {
            petServe.setComment(comment);
        }
        petServe.setServeName(serveName);
        petServe.setUpdate_time(update_time);
        petServe.setPrice(price);
        petServeMapper.updateById(petServe);
    }

    @Override
    public Integer countPetServe() {
        QueryWrapper<PetServe> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return petServeMapper.selectCount(wrapper);
    }

    @Override
    public List<PetServe> getAllPetServes() {
        QueryWrapper<PetServe> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return petServeMapper.selectList(wrapper);
    }
}
