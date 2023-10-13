package com.example.demo.controller;


import com.example.demo.entity.Users;
import com.example.demo.service.IUsersService;
import com.example.demo.utils.JwtTokenUtil;
import com.example.demo.utils.PageDataResult;
import com.example.demo.utils.PageDataResultUtils;
import com.example.demo.utils.RequestBody.Users.PageLoginResultUtils;
import com.example.demo.utils.RequestBody.Users.PageLoginResult;
import com.example.demo.utils.RequestBody.Users.UsersLoginObject;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("page") Integer page,
                                 @RequestParam("username") String username,
                                 @RequestParam("management") String management,
                                 @RequestParam("size") Integer size) {
        Integer start = (page - 1) * size + 1;
        List<Users> users = usersService.getUsers(username, management, start, size);
        PageDataResult<Object> result = PageDataResultUtils.success(users);
        result.setMessage("select success");
        return result;
    }

    @PostMapping("/login")
    public PageLoginResult<Object> login(@RequestBody UsersLoginObject requestBody) {
        String username = requestBody.getUsername();
        String password = requestBody.getPassword();
        if (usersService.checkPassowrd(username, password)) {
            PageLoginResult<Object> result = PageLoginResultUtils.success();
            String token = jwtTokenUtil.generateToken(username);
            result.setUsername(username);
            result.setPassword(password);
            result.setToken(token);
            return result;
        } else {
            PageLoginResult<Object> result = PageLoginResultUtils.fail();
            result.setUsername(username);
            result.setPassword(password);
            result.setToken("please retry");
            return result;
        }
    }
}
