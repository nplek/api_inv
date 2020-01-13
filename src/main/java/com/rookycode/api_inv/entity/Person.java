package com.rookycode.api_inv.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder(builderClassName = "PersonBuilder", toBuilder = true)
@JsonDeserialize(builder = Person.PersonBuilder.class)
@Data
@ToString(of={"firstname", "lastname"})
public class Person {
    private int id;
    private String firstname;
    private String lastname;
    private double age;
}