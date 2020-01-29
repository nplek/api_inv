package com.rookycode.api_inv.service;

import com.rookycode.api_inv.entity.Person;
import com.rookycode.api_inv.repository.PersonRepository;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
@Log
@Service
@Valid
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
	
	@Cacheable(value = "personById", key = "#id", unless = "#result==null")
	public Optional<Person> getPersonById(int id) {
		log.info("getPerson ID: " + id);
		return personRepository.findById(id);
	}

	@Cacheable(value="personPage", key="T(java.lang.String).format('O:%s-L:%s',#offset, #limit)", unless = "#result==null")
	public Page<Person> getPagePersons(int offset,int limit){
		log.info("getPagePersons offset: " + offset + " limit: " + limit);
		return personRepository.findAll(PageRequest.of(offset, limit));
	}

	@Caching(evict = { @CacheEvict(value = "personPage", allEntries = true )})
	public Person addPerson(Person person){
		log.info("Add person: " + person.getFirstname() );
		return personRepository.save(person);
	}

	@Caching(evict = { @CacheEvict(value = "personPage", allEntries = true ), @CacheEvict(value = "personById", key = "#id"), @CacheEvict(value = "personByName")})
	@CachePut(value = "person", key = "#id")
	public Optional<Person> updatePersonById(int id, Person person) {
		log.info("Update person ID:" + id);
		Optional<Person> personOpt = personRepository.findById(id);
        if(!personOpt.isPresent()) {
            return Optional.empty();
        }
        person.setId(id);
		return Optional.of(personRepository.save(person));
	}

	@Caching(evict = { @CacheEvict(value = "personPage", allEntries = true ), @CacheEvict(value = "personById", key = "#id"), @CacheEvict(value = "personByName")})
	@CacheEvict(value = "person", allEntries = true)
	public boolean deletePerson(int id) {
		log.info("Delete person id: " + id);
		try {
            personRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
	}
}