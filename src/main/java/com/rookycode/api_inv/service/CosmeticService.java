package com.rookycode.api_inv.service;

import com.rookycode.api_inv.entity.Cosmetic;
import com.rookycode.api_inv.repository.CosmeticRepository;

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
public class CosmeticService {

    @Autowired
    private CosmeticRepository cosmeticRepository;
	
	@Cacheable(value = "cosmeticById", key = "#id", unless = "#result==null")
	public Optional<Cosmetic> getCosmeticById(Long id) {
		log.info("getCosmetic ID: " + id);
		return cosmeticRepository.findById(id);
	}

	//@Cacheable(value="cosmeticPage", key="T(java.lang.String).format('%s-O:%s-L:%s',#root.methodName,#offset, #limit)", unless = "#result==null")
	@Cacheable(value="cosmeticPage", key="T(java.lang.String).format('O:%s-L:%s',#offset, #limit)", unless = "#result==null")
	public Page<Cosmetic> getPageCosmetics(int offset,int limit){
		log.info("getPageCosmetics offset: " + offset + " limit: " + limit);
		return cosmeticRepository.findAll(PageRequest.of(offset, limit));
	}

	@Caching(evict = { @CacheEvict(value = "cosmeticPage", allEntries = true )})
	public Cosmetic addCosmetic(Cosmetic cosmetic){
		log.info("Add cosmetic: " + cosmetic.getName() );
		return cosmeticRepository.save(cosmetic);
	}

	@Caching(evict = { @CacheEvict(value = "cosmeticPage", allEntries = true ), @CacheEvict(value = "cosmeticById", key = "#id"), @CacheEvict(value = "cosmeticByName")})
	@CachePut(value = "cosmetic", key = "#id")
	public Optional<Cosmetic> updateCosmeticById(Long id, Cosmetic cosmetic) {
		log.info("Update cosmetic ID:" + id);
		Optional<Cosmetic> cosmeticOpt = cosmeticRepository.findById(id);
        if(!cosmeticOpt.isPresent()) {
            return Optional.empty();
        }
        cosmetic.setId(id);
		return Optional.of(cosmeticRepository.save(cosmetic));
	}

	@Caching(evict = { @CacheEvict(value = "cosmeticPage", allEntries = true ), @CacheEvict(value = "cosmeticById", key = "#id"), @CacheEvict(value = "cosmeticByName")})
	@CacheEvict(value = "cosmetic", allEntries = true)
	public boolean deleteCosmetic(Long id) {
		log.info("Delete cosmetic id: " + id);
		try {
            cosmeticRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
	}
}