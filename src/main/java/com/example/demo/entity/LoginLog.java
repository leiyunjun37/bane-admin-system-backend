package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@TableName("loginlog")
public class LoginLog {

    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @TableField("username")
    @Getter
    @Setter
    private String username;

    @TableField("logintime")
    @Setter
    @Getter
    private String logintime;

    @TableField("is_delete")
    @Setter
    private Integer is_delete;
}
