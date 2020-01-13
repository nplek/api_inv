package com.rookycode.api_inv.controller;

import java.util.ArrayList;
import java.util.List;

import com.rookycode.api_inv.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        log.info("user list");
        List<User> userList = new ArrayList<User>();
        User usr1 = User.builder().name("name1").build();
        User usr2 = User.builder().name("name2").build();
        User usr3 = User.builder().name("name3").build();
        userList.add(usr1);
        userList.add(usr2);
        userList.add(usr3);
        
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }
}