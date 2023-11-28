package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // 此注解生成无参构造函数
@AllArgsConstructor // 此注解生成全参构造函数
@TableName("pets")
public class Pets {

    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @TableField("petname")
    @Getter
    @Setter
    private String petname;

    @TableField("owner")
    @Setter
    @Getter
    private String owner;

    @TableField("create_time")
    @Setter
    private String create_time;

    @TableField("update_time")
    @Setter
    private String update_time;

    @TableField("age")
    @Setter
    @Getter
    private Integer age;

    @TableField("variety")
    @Setter
    @Getter
    private String variety;

    @TableField("is_delete")
    @Setter
    private Integer is_delete;

}
