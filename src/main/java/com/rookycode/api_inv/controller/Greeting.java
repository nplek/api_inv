package com.rookycode.api_inv.controller;

import java.util.ArrayList;

//import javax.validation.Valid;

import com.rookycode.api_inv.entity.Cosmetic;
import com.rookycode.api_inv.entity.Person;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@Log
@RestController
class Greeting {
    @GetMapping("/greeting")
    public String hello() {
        log.info("Geeting");
        return "Hello";
    }

    @GetMapping("/person")
    public Person getPersion() {
        log.info("getPerson");
        Person person = Person.builder().firstname("firstname").lastname("lastname").age(33).build();
        return person;
    }

    @PostMapping("/person")
    public Person getPPersion() {
        log.info("getPPerson");
        Person person = Person.builder().firstname("firstname").lastname("lastname").age(33).build();
        return person;
    }

    @PostMapping(value = "/cmt", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCosmetic(@RequestBody Cosmetic cosmetic) {
        log.info("Controller post: " + cosmetic);
        return ResponseEntity.status(HttpStatus.OK).body(cosmetic);
    }

    @GetMapping(value="/persons")
    public ResponseEntity<?> getPersons() {
        log.info("getPersons");
        Person p1 = Person.builder().firstname("firstname1").lastname("lastname1").age(15).build();
        Person p2 = Person.builder().firstname("firstname2").lastname("lastname2").age(25).build();
        Person p3 = Person.builder().firstname("firstname3").lastname("lastname3").age(35).build();
        ArrayList<Person> person = new ArrayList<Person>();
        person.add(p1);
        person.add(p2);
        person.add(p3);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}