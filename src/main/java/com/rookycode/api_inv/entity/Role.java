package com.rookycode.api_inv.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(builderClassName = "RoleBuilder", toBuilder = true)
@JsonDeserialize(builder = Role.RoleBuilder.class)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(of={"email"})
@Table(name="roles")
public class Role implements Serializable {
    
    
    /**
     *
     */
    private static final long serialVersionUID = -8923135813202624403L;

    @JsonProperty("id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("name")
    @NotNull(message = "{role.name.notNull}")
    @Size(min=3,max=50, message = "{role.name.size}")
    @Column(length = 50, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;  
}