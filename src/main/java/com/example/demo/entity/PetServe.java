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
@TableName("petserve")
public class PetServe {

    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @TableField("serveName")
    @Getter
    @Setter
    private String serveName;

    @TableField("price")
    @Getter
    @Setter
    private Integer price;

    @TableField("comment")
    @Getter
    @Setter
    private String comment;

    @TableField("is_delete")
    @Setter
    private Integer is_delete;

    @TableField("create_time")
    @Setter
    private String create_time;

    @TableField("update_time")
    @Setter
    private String update_time;

}
