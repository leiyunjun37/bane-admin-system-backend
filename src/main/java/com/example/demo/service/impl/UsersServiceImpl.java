package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Users;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private CommonUtils commonUtils;

    @Override
    public List<Users> getUsers(String username, String management, Integer start, Integer size) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.like("username", username);
        wrapper.like("management", management);
        wrapper.eq("is_delete", 0);
        Page<Users> page = new Page<>(start, size);
        return usersMapper.selectPage(page, wrapper).getRecords();
    }

    @Override
    public void insertUsers(String username, String password, String management, String encryptedpassword) {
        Users user = new Users();
        user.setUsername(username);
        user.setIs_delete(0);
        user.setPassword(password);
        user.setManagement(management);
        user.setLastlogin("-");
        user.setEncryptedpassword(encryptedpassword);
        usersMapper.insert(user);
    }

    @Override
    public Boolean checkPassword(String username, String md5password) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("is_delete", 0);
        Users user = usersMapper.selectOne(wrapper);
        if (user != null) {
            return Objects.equals(user.getEncryptedpassword(), md5password);
        } else {
            return false;
        }
    }

    @Override
    public Boolean checkUsernameUnique(String username) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username)
                .eq("is_delete", 0);
        Users exist = usersMapper.selectOne(wrapper);
        return exist == null;
    }

    @Override
    public void login(String username, String lastlogin) {
        Users user = new Users();
        user.setUsername(username);
        user.setIs_delete(0);
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username)
                .eq("lastlogin", lastlogin);
        usersMapper.update(user, wrapper);
    }

    @Override
    public Boolean delete(Integer id) {
        Users users = new Users();
        users.setId(id);
        users.setIs_delete(1);
        usersMapper.updateById(users);
        return true;
    }

    @Override
    public Boolean checkPasswordInvalid(String password) {
        if (password == null || password.length() < 8 || password.length() > 16) {
            return false;
        }
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^0-9a-zA-Z])(?=\\S+$).{8,16}$";
        return password.matches(regex);
    }

    @Override
    public void update(String username, String management, Integer id) {
        Users users = new Users();
        users.setId(id);
        users.setUsername(username);
        users.setManagement(management);
    }

    @Override
    public Integer countUser() {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        return usersMapper.selectCount(wrapper);
    }

    @Override
    public void changePassword(String password, Integer id) {
        Users users = new Users();
        users.setId(id);
        users.setPassword(password);
        users.setEncryptedpassword(commonUtils.md5(password));
        usersMapper.updateById(users);
    }

}
