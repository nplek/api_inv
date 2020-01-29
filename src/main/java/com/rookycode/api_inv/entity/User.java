package com.rookycode.api_inv.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(builderClassName = "UserBuilder", toBuilder = true)
@JsonDeserialize(builder = User.UserBuilder.class)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(of={"email"})
@Table(name="users",  uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4717441324999584325L;
    
    @JsonProperty("id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("name")
    @NotNull(message = "{user.name.notNull}")
    @Size(min=3,max=50, message = "{user.name.size}")
    @Column(length = 50, nullable = false)
    private String name;

    @JsonProperty("password")
    @JsonIgnore
    @NotNull(message = "{user.password.notNull}")
    @Size(min=5, max=100, message = "{user.password.size}")
    @Column(length = 100, nullable = false)
    private String password;
    
    @JsonProperty("email")
    @NotNull(message = "{user.email.notNull}")
    @Email(message = "{user.email.format")
    @Size(max=50, message = "{user.email.size}")
    @Column(columnDefinition = "varchar(50) NOT NULL")
    private String email;
    
    @JsonProperty("enabled")
    @Column(columnDefinition = "boolean default true")
    private String enabled;
}