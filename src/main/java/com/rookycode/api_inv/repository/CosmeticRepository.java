package com.rookycode.api_inv.repository;

import com.rookycode.api_inv.entity.Cosmetic;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CosmeticRepository extends PagingAndSortingRepository<Cosmetic,Long> {

}