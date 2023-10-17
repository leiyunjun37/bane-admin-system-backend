package com.example.demo.controller;


import com.example.demo.entity.Users;
import com.example.demo.service.ILoginLogService;
import com.example.demo.service.IUsersService;
import com.example.demo.utils.*;
import com.example.demo.utils.RequestBody.Users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;

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
    private ILoginLogService loginLogService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CommonUtils commonUtils;

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("page") Integer page,
                                 @RequestParam("username") String username,
                                 @RequestParam("management") String management,
                                 @RequestParam("size") Integer size) {
        try {
            Integer start = (page - 1) * size + 1;
            List<Users> users = usersService.getUsers(username, management, start, size);
            Integer total = usersService.countUser();
            PageDataResult<Object> result = PageDataResultUtils.success(users, total);
            result.setMessage("select success");
            return result;
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/login")
    public PageLoginResult<Object> login(@RequestBody UsersLoginObject requestBody) {
        try {
            String username = requestBody.getUsername();
            String md5Password = requestBody.getPassword();
            if (usersService.checkPassword(username, md5Password)) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String lastlogin = now.format(formatter);
                usersService.login(username, lastlogin);
                loginLogService.insert(username, lastlogin);
                PageLoginResult<Object> result = PageLoginResultUtils.success();
                String token = jwtTokenUtil.generateToken(username);
                result.setUsername(username);
                result.setPassword(md5Password);
                result.setToken(token);
                return result;
            } else {
                PageLoginResult<Object> result = PageLoginResultUtils.fail();
                result.setUsername(username);
                result.setPassword(md5Password);
                result.setToken("invalid password");
                return result;
            }
        } catch (Exception e) {
            PageLoginResult<Object> result = PageLoginResultUtils.fail();
            result.setUsername(null);
            result.setPassword(null);
            result.setToken(e.getMessage());
            return result;
        }

    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody UsersInsertObject requestBody) {
        try {
            String username = requestBody.getUsername();
            if (usersService.checkUsernameUnique(username)) {
                String password = requestBody.getPassword();
                if (usersService.checkPasswordInvalid(password)) {
                    String md5Password = commonUtils.md5(password);
                    String management = requestBody.getManagement();
                    usersService.insertUsers(username, password, management, md5Password);
                    PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                    result.setMessage("insert success");
                    return result;
                } else {
                    PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                    result.setMessage("invalid password");
                    return result;
                }
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("the username already exist");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @DeleteMapping("/delete")
    public PageNoneDataResult<Object> delete(@RequestParam("id") Integer id) {
        try {
            if (usersService.delete(id)) {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("delete success");
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("delete falid, id not exist or invalid");
                return result;
            }
        } catch (Exception E) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(E.getMessage());
            return result;
        }
    }

    @PatchMapping("/changePassword")
    public PageNoneDataResult<Object> changePassword(@RequestBody UsersChangePassObject requestBody) {
        try {
            String newPassword = requestBody.getPassword();
            if (usersService.checkPasswordInvalid(newPassword)) {
                Integer id = requestBody.getId();
                usersService.changePassword(newPassword, id);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("change password success");
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("the invalid password");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PatchMapping("/patch")
    public PageNoneDataResult<Object> update(@RequestBody UsersUpdateObject requestBody) {
        try {
            String username = requestBody.getUsername();
            System.out.println(username);
            if (usersService.checkUsernameUnique(username)) {
                Integer id = requestBody.getId();
                String management = requestBody.getManagement();
                usersService.update(username, management, id);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("update success");
                return result;
            } else {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
                result.setMessage("the name already exist");
                return result;
            }
        } catch (Exception e) {
            PageNoneDataResult<Object> result = PageNoneDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }
}
