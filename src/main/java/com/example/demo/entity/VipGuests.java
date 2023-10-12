package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

//@Data // 此注解自动生成getter和setter，toString，equals和hashCode等方法
@NoArgsConstructor // 此注解生成无参构造函数
@AllArgsConstructor // 此注解生成全参构造函数
@TableName("vipguests")
public class VipGuests {

    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    private Integer id;

    @TableField("name")
    @Getter
    @Setter
    private String name;

    @TableField("balance")
    @Getter
    @Setter
    private int balance;

    @TableField("conway")
    @Getter
    @Setter
    private String conway;

    @TableField("lastshop")
    @Getter
    @Setter
    private String lastshop = "-";

    @TableField("registertime")
    @Getter
    @Setter
    private String registertime = "-";

    @TableField("is_delete")
    @Setter
    private Integer is_delete = 0;

}
