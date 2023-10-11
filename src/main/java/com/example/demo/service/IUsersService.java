package com.example.demo.service;

import com.example.demo.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

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

    List<Users> getUsers(String username, String management, Integer start, Integer size);

}
