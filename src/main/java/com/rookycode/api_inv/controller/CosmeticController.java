package com.rookycode.api_inv.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.rookycode.api_inv.entity.Cosmetic;
import com.rookycode.api_inv.service.CosmeticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@Log
@RestController
//@RequestMapping("/cosmetics")
public class CosmeticController {

    @Autowired
    private CosmeticService cosmeticService;
    @GetMapping("/cosmetics/{id}")
    public ResponseEntity<?> getCosmetic(@PathVariable Long id) {
        Optional<Cosmetic> cosmetic = cosmeticService.getCosmeticById(id);
        if (!cosmetic.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cosmetic);
    }

    @GetMapping(value = "/cosmetics")
    public ResponseEntity<Page<Cosmetic>> getAllCosmetics(@RequestParam(name = "offset", defaultValue = "0") int offset,
    @RequestParam(name = "limit", defaultValue = "5") int limit) {
        return new ResponseEntity<Page<Cosmetic>>(cosmeticService.getPageCosmetics(offset, limit), HttpStatus.OK);
    }
    
    @PostMapping(value="/cosmetics",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCosmetic(@RequestBody @Valid Cosmetic cosmetic) {
        log.info("Controller post: " + cosmetic);
        Cosmetic cmt = cosmeticService.addCosmetic(cosmetic);
        return ResponseEntity.status(HttpStatus.CREATED).body(cmt);
    }

    @PutMapping(value="/cosmetics/{id}")
    public ResponseEntity<?> updateCosmetic(@PathVariable Long id, @RequestBody Cosmetic cosmetic) {
        Optional<Cosmetic> cmt = cosmeticService.updateCosmeticById(id, cosmetic);
        if (!cmt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cmt);
    }

    @DeleteMapping(value="/cosmetics/{id}")
    public ResponseEntity<?> deleteCosmetic(@PathVariable Long id) {
        if (!cosmeticService.deleteCosmetic(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}