package com.rookycode.api_inv.entity;

import java.io.Serializable;

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

@Data
@Entity
@Builder(builderClassName = "CosmeticBuilder", toBuilder = true)
@JsonDeserialize(builder = Cosmetic.CosmeticBuilder.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cosmetic")
public class Cosmetic implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6535934262825989591L;
    @JsonProperty("id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonProperty("name")
    @NotNull(message = "{cosmetic.name.notNull}")
    @Size(min=3,max=60, message = "{cosmetic.name.size}")
    private String name;
    @JsonProperty("amount")
    @NotNull(message = "{cosmetic.amount.notNull}")
    @PositiveOrZero(message = "{cosmetic.amount.positiveOrZero}")
    private int amount;
}