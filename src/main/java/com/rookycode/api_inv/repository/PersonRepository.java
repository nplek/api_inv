package com.rookycode.api_inv.repository;

import com.rookycode.api_inv.entity.Person;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, Integer> {

}