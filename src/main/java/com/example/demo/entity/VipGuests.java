package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 此注解自动生成getter和setter，toString，equals和hashCode等方法
@NoArgsConstructor // 此注解生成无参构造函数
@AllArgsConstructor // 此注解生成全参构造函数
@TableName("vipguests")
public class VipGuests {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("name")
    private String name;

    @TableField("balance")
    private int balance;

    @TableField("conway")
    private String conway;

    @TableField("lastshop")
    private String lastshop = "-";

    @TableField("registertime")
    private String registertime = "-";

    @TableField("is_delete")
    private Integer is_delete = 0;

}
