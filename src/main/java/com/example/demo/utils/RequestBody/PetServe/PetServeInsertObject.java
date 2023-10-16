package com.example.demo.utils.RequestBody.PetServe;

import lombok.Data;

@Data
public class PetServeInsertObject {

    private String serveName;

    private Integer price;

    private String unit;

    private String comment;
}
