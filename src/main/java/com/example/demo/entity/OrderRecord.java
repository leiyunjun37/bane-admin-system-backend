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
@TableName("orderecord")
public class OrderRecord {

    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @TableField("name")
    @Getter
    @Setter
    private String name;

    @TableField("is_vipguest")
    @Getter
    @Setter
    private Integer is_vipguest;

    @TableField("datetime")
    @Getter
    @Setter
    private String datetime;

    @TableField("comment")
    @Getter
    @Setter
    private String comment;

    @TableField("is_delete")
    @Setter
    private Integer is_delete;
}
