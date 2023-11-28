package com.example.demo.controller;


import com.example.demo.entity.Users;
import com.example.demo.service.ILoginLogService;
import com.example.demo.service.IOperationLogService;
import com.example.demo.service.IUsersService;
import com.example.demo.utils.*;
import com.example.demo.utils.RequestBody.Users.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 'sys.user_summary_by_statement_type' is not BASE TABLE 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2023-10-10
 */
@CrossOrigin(value = "http://localhost:8080")
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ILoginLogService loginLogService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private IOperationLogService operationLogService;

    public static String pageInfo = "用户信息";

    public UsersController() {
    }

    @GetMapping("/get")
    public PageDataResult<Object> select(@RequestParam("page") Integer page,
                                         @RequestParam("username") String username,
                                         @RequestParam("management") String management,
                                         @RequestParam("size") Integer size) {
        try {
            if (size == -1) {
                List<Users> users = usersService.getAllUsers();
                Integer total = usersService.countUser();
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", users);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            } else {
                Integer start = (page - 1) * size;
                Integer end = start + size;
                HashMap<String, Object> hashMap = usersService.getUsers(username, management);
                Integer total = (Integer) hashMap.get("total");
                List<Users> data = (List<Users>) hashMap.get("data");
                List<Users> users;
                if (total < size) {
                    users = data.subList(start, total);
                } else {
                    users = data.subList(start, end);
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("records", users);
                map.put("total", total);
                PageDataResult<Object> result = PageDataResultUtils.success(map);
                result.setMessage("select success");
                return result;
            }
        } catch (Exception e) {
            PageDataResult<Object> result = PageDataResultUtils.fail();
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/logout")
    public PageLogoutResult<Object> logout(@RequestBody UsersLogoutObject requestBody) {
        try {
            String username = requestBody.getUsername();
            if (Boolean.TRUE.equals(redisTemplate.hasKey(username))) {
                redisTemplate.delete(username);
                return PageLogoutResultUtils.success();
            } else {
                PageLogoutResult<Object> result = PageLogoutResultUtils.success();
                result.setMessage("have no this user login record");
                return result;
            }
        } catch (Exception e) {
            PageLogoutResult<Object> result = PageLogoutResultUtils.success();
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
                String type = usersService.login(username, lastlogin);
                loginLogService.insert(username, lastlogin);
                PageLoginResult<Object> result = PageLoginResultUtils.success();
                String token = jwtTokenUtil.generateToken(username);
                redisTemplate.opsForValue().set(username, token, 4, TimeUnit.HOURS);
                HashMap<String, Object> data = new HashMap<>();
                data.put("username", username);
                data.put("token", token);
                data.put("management", type);
                result.setData(data);
                return result;
            } else {
                PageLoginResult<Object> result = PageLoginResultUtils.fail();
                HashMap<String, Object> data = new HashMap<>();
                result.setData(data);
                result.setMessage("invalid password");
                return result;
            }
        } catch (Exception e) {
            PageLoginResult<Object> result = PageLoginResultUtils.fail();
            HashMap<String, Object> data = new HashMap<>();
            result.setData(data);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    @PostMapping("/post")
    public PageNoneDataResult<Object> insert(@RequestBody UsersInsertObject requestBody, HttpServletRequest request) {
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
                    String token = request.getHeader("Authorization").substring("Bearer ".length());
                    String name = jwtTokenUtil.getUsernameFromToken(token);
                    String type = "新增";
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = now.format(formatter);
                    operationLogService.insert(name, type, pageInfo, formattedDateTime);
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
    public PageNoneDataResult<Object> delete(@RequestParam("id") Integer id, HttpServletRequest request) {
        try {
            if (usersService.delete(id)) {
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("delete success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "删除";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
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
    public PageNoneDataResult<Object> changePassword(@RequestBody UsersChangePassObject requestBody, HttpServletRequest request) {
        try {
            String newPassword = requestBody.getPassword();
            if (usersService.checkPasswordInvalid(newPassword)) {
                Integer id = requestBody.getId();
                usersService.changePassword(newPassword, id);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("change password success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String type = "修改密码";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                operationLogService.insert(username, type, pageInfo, formattedDateTime);
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
    public PageNoneDataResult<Object> update(@RequestBody UsersUpdateObject requestBody, HttpServletRequest request) {
        try {
            String username = requestBody.getUsername();
            System.out.println(username);
            if (usersService.checkUsernameUnique(username)) {
                Integer id = requestBody.getId();
                String management = requestBody.getManagement();
                usersService.update(username, management, id);
                PageNoneDataResult<Object> result = PageNoneDataResultUtils.success();
                result.setMessage("update success");
                String token = request.getHeader("Authorization").substring("Bearer ".length());
                String name = jwtTokenUtil.getUsernameFromToken(token);
                String type = "编辑";
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                operationLogService.insert(name, type, pageInfo, formattedDateTime);
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
