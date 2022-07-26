package com.codedifferently.firebaseapiauthenticationdemo.domain.users.controller;

import com.codedifferently.firebaseapiauthenticationdemo.domain.users.service.UserService;
import com.codedifferently.firebaseapiauthenticationdemo.security.models.FireBaseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<FireBaseUser> getUserInfo(@AuthenticationPrincipal FireBaseUser user) {
        log.info("A request was made by user with id {} and email {}",user.getUid(), user.getEmail());
        Boolean saved = userService.doesExist(user.getUid());
        log.info("Is the user saved {}", saved);
        return ResponseEntity.ok(user);
    }

}