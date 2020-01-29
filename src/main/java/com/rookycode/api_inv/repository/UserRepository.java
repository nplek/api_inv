package com.rookycode.api_inv.repository;

import java.util.Optional;

import com.rookycode.api_inv.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    @Query("SELECT t.name, t.email FROM users t where t.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT t.name, t.email FROM users t WHERE t.name LIKE :name")
    Optional<User> findByName(@Param("name") String name);

    @Query("DELETE users t where t.email = :email")
    void deleteByEmail(@Param("email") String email);
}