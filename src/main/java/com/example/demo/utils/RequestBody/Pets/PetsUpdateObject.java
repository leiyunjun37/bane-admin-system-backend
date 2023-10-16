package com.example.demo.utils.RequestBody.Pets;


import lombok.Data;
import sun.dc.pr.PRError;

@Data
public class PetsUpdateObject {

    private Integer id;

    private String petname;

    private String owner;

    private Integer age;

    private String variety;
}


