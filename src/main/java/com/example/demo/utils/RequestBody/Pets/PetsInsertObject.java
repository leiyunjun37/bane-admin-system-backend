package com.example.demo.utils.RequestBody.Pets;

import lombok.Data;

@Data
public class PetsInsertObject {

    private String petname;

    private String owner;

    private String variety;

    private Integer age;
}
