package com.example.demo.controller;


import com.example.demo.entity.Users;
import com.example.demo.service.IUsersService;
import com.example.demo.utils.PageResult;
import com.example.demo.utils.PageResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 'sys.user_summary_by_statement_type' is not BASE TABLE 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-10-10
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @GetMapping("/get")
    public PageResult select(@RequestParam("page") Integer page, @RequestParam("username") String username, @RequestParam("management") String management, @RequestParam("size") Integer size) {
        Integer start = (page - 1) * size;
        Integer end = start + size;
        List<Users> users = usersService.getUsers(username, management, start, end);
        return PageResultUtils.success(users);
    }
}
