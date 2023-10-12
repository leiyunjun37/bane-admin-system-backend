package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


/**
* <p>
    * 'sys.user_summary_by_statement_type' is not BASE TABLE
    * </p>
*
* @author jobob
* @since 2023-10-10
*/
//@Data // 此注解自动生成getter和setter，toString，equals和hashCode等方法
@NoArgsConstructor // 此注解生成无参构造函数
@AllArgsConstructor // 此注解生成全参构造函数
@TableName("users")
public class Users {

    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @TableField("username")
    @Getter
    @Setter
    private String username;

    @TableField("password")
    @Setter
    private String password;

    @TableField("management")
    @Setter
    @Getter
    private String management;

    @TableField("lastlogin")
    @Setter
    @Getter
    private String lastlogin;

    @TableField("is_delete")
    @Setter
    private Integer is_delete;

}