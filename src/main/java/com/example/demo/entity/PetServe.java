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

    @TableField("unit")
    @Getter
    @Setter
    private String unit;

    @TableField("comment")
    @Getter
    @Setter
    private String comment;

    @TableField("is_delete")
    @Setter
    private Integer is_delete;

}
