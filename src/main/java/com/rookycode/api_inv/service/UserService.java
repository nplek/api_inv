package com.rookycode.api_inv.service;

import com.rookycode.api_inv.entity.User;
import com.rookycode.api_inv.repository.UserRepository;

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
public class UserService {

    @Autowired
    private UserRepository userRepository;
	
	@Cacheable(value = "userById", key = "#id", unless = "#result==null")
	public Optional<User> getUserByName(String name) {
		log.info("getUser Name: " + name);
		return userRepository.findByName(name + "%");
	}

	@Cacheable(value="userPage", key="T(java.lang.String).format('O:%s-L:%s',#offset, #limit)", unless = "#result==null")
	public Page<User> getPageUsers(int offset,int limit){
		log.info("getPageUsers offset: " + offset + " limit: " + limit);
		return userRepository.findAll(PageRequest.of(offset, limit));
	}

	@Caching(evict = { @CacheEvict(value = "userPage", allEntries = true )})
	public User addUser(User user){
		log.info("Add user: " + user.getName() );
		return userRepository.save(user);
	}

	@Caching(evict = { @CacheEvict(value = "userPage", allEntries = true ), @CacheEvict(value = "userById", key = "#id"), @CacheEvict(value = "userByName")})
	@CachePut(value = "user", key = "#email")
	public Optional<User> updateUserByEmail(String email, User user) {
		log.info("Update user email:" + email);
		Optional<User> userOpt = userRepository.findByEmail(email);
        if(!userOpt.isPresent()) {
            return Optional.empty();
        }
        user.setEmail(email);
		return Optional.of(userRepository.save(user));
	}

	@Caching(evict = { @CacheEvict(value = "userPage", allEntries = true ), @CacheEvict(value = "userById", key = "#id"), @CacheEvict(value = "userByName")})
	@CacheEvict(value = "user", allEntries = true)
	public boolean deleteUser(String email) {
		log.info("Delete user id: " + email);
		try {
            userRepository.deleteByEmail(email);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
	}
}