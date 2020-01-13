package com.rookycode.api_inv.service;

import com.rookycode.api_inv.entity.Cosmetic;
import com.rookycode.api_inv.repository.CosmeticRepository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
@Log
@Service
@Valid
public class CosmeticService {

    @Autowired
    private CosmeticRepository cosmeticRepository;

	@Cacheable(value = "cosmetic", key = "#id", unless = "#result==null")
    public Cosmetic getCosmetic(Long id) {
		log.info("getCosmetic ID: " + id);
        return cosmeticRepository.getOne(id);
	}
	
	@Cacheable(value = "cosmetic", key = "#id", unless = "#result==null")
	public Optional<Cosmetic> getCosmeticById(Long id) {
		return cosmeticRepository.findById(id);
	}
	public List<Cosmetic> getAllCosmetics(){
		return cosmeticRepository.findAll();
	}

	public Cosmetic addCosmetic(Cosmetic cosmetic){
		log.info("Add cosmetic: " + cosmetic.getName() );
		return cosmeticRepository.save(cosmetic);
	}

	public Cosmetic createCosmetic(Cosmetic cosmetic) {
		return cosmeticRepository.save(cosmetic);
	}

	@CachePut(value = "cosmetic", key = "#id")
	public Optional<Cosmetic> updateCosmeticById(Long id, Cosmetic cosmetic) {
		Optional<Cosmetic> cosmeticOpt = cosmeticRepository.findById(id);
        if(!cosmeticOpt.isPresent()) {
            return Optional.empty();
        }
        cosmetic.setId(id);
		return Optional.of(cosmeticRepository.save(cosmetic));
	}

	@CacheEvict(value = "cosmetic", allEntries = true)
	public boolean deleteCosmetic(Long id) {
		cosmeticRepository.deleteById(id);
		try {
            cosmeticRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
	}
}