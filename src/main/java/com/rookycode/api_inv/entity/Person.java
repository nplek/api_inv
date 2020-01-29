package com.rookycode.api_inv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(builderClassName = "PersonBuilder", toBuilder = true)
@JsonDeserialize(builder = Person.PersonBuilder.class)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(of={"firstname", "lastname"})
@Table(name="person")
public class Person implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8919230782008998404L;
    
    @JsonProperty("id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @JsonProperty("firstname")
    @NotNull(message = "{person.firstname.notNull}")
    @Size(min=3,max=50, message = "{person.firstname.size}")
    @Column(nullable = false, length = 50)
    private String firstname;
    
    @JsonProperty("lastname")
    @Size(max=50, message = "{person.lastname.size}")
    @Column(length = 50)
    private String lastname;
    
    @JsonProperty("age")
    @NotNull(message = "{person.age.notNull}")
    @PositiveOrZero(message = "{person.age.positiveOrZero}")
    @Column(columnDefinition = "integer", nullable = false)
    private double age;
}