package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("vipguests")
public class VipGuests {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("balance")
    private Integer balance;

    @TableField("conway")
    private String conway;

    @TableField("lastshop")
    private String lastshop = "-";

    @TableField("registertime")
    private String registertime = "-";

    @TableField("is_delete")
    private Integer is_delete = 0;

    public Integer getId() {
        return id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getConway() {
        return conway;
    }

    public void setConway(String conway) {
        this.conway = conway;
    }

    public String getLastshop() {
        return lastshop;
    }

    public void setLastshop(String lastshop) {
        this.lastshop = lastshop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistertime() {
        return registertime;
    }

    public void setRegistertime(String registertime) {
        this.registertime = registertime;
    }

    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }
}
