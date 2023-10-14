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
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        if (usersMapper.selectOne(wrapper) != null) {
            Users users = new Users();
            users.setId(id);
            QueryWrapper<Users> wrapperr = new QueryWrapper<>();
            wrapper.eq("id", id)
                    .eq("is_delete", 1);
            usersMapper.update(users, wrapperr);
            return true;
        } else {
            return false;
        }
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
    public Boolean update(String[] cols, Object[] values) {
        if (Arrays.asList(cols).contains("id")){
            QueryWrapper<Users> wrapper = new QueryWrapper<>();
            Users users = new Users();
            for (int i = 0; i < cols.length; i++) {
                if (cols[i].equals("id")) {
                    users.setId((Integer) values[i]);
                } else if (cols[i].equals("password")) {
                    String password = (String) values[i];
                    wrapper.eq("password", password)
                            .eq("encryptedpassword", commonUtils.md5(password));
                } else {
                    wrapper.eq(cols[i], values[i]);
                }
            }
            usersMapper.update(users, wrapper);
            return true;
        } else {
            return false;
        }
    }

}
