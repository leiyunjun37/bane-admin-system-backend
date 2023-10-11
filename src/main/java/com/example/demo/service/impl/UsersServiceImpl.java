package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Users;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 'sys.user_summary_by_statement_type' is not BASE TABLE 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2023-10-10
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<Users> getUsers(String username, String management, Integer start, Integer end) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        wrapper.like("management", management);
        wrapper.eq("is_delete", 0);
        Page<Users> page = new Page<>(start, end);
        return usersMapper.selectPage(page, wrapper).getRecords();
    }

}
