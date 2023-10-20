package com.example.demo.service;

import com.example.demo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 'sys.user_summary_by_statement_type' is not BASE TABLE 服务类
 * </p>
 *
 * @author jobob
 * @since 2023-10-10
 */
public interface IUsersService extends IService<Users> {

    HashMap<String, Object> getUsers(String username, String management, Integer start, Integer size);

    void insertUsers(String username, String password, String management, String encryptedpassword);

    Boolean checkPassword(String username, String password);

    Boolean checkUsernameUnique(String username);

    void login(String username, String lastlogin);

    Boolean delete(Integer id);

    Boolean checkPasswordInvalid(String password);

    void update(String username, String management, Integer id);

    Integer countUser();

    void changePassword(String password, Integer id);

    List<Users> getAllUsers();

}
