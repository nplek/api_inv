package com.rookycode.api_inv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(builderClassName = "AuthoritieBuilder", toBuilder = true)
@JsonDeserialize(builder = Authoritie.AuthoritieBuilder.class)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(of={"authority"})
@Table(name="authorities")
/*@Table(name="authorities",uniqueConstraints={
    @UniqueConstraint(columnNames = {"email", "authority"})
}) */
public class Authoritie implements Serializable {
    
    /**
	 *
	 */
	private static final long serialVersionUID = -5169820057387587040L;

	@JsonProperty("authority")
    @NotNull(message = "{authorities.authority.notNull}")
    @Column(length = 50, nullable = false)
    
    private String authority;
    
    @JsonProperty("email")
    @NotNull(message = "{authorities.authority.notNull}")
    @Column(length = 50, nullable = false)
    private String email;
}