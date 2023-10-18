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
@TableName("operationlog")
public class OperationLog {

    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @TableField("username")
    @Getter
    @Setter
    private String username;

    @TableField("page")
    @Setter
    @Getter
    private String page;

    @TableField("type")
    @Setter
    @Getter
    private String type;

    @TableField("datetime")
    @Setter
    @Getter
    private String datetime;

    @TableField("is_delete")
    @Setter
    private Integer is_delete;
}
