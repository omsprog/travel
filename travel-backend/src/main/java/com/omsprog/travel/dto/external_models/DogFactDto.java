package com.omsprog.travel.dto.external_models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DogFactDto {
    public ArrayList<DogFact> data;
}

@Data
class DogFact {
    public String id;
    public String type;
    public Attributes attributes;
}

@Data
class Attributes {
    private String body;
}
