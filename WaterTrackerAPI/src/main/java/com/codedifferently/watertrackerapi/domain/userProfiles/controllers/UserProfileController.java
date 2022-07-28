package com.codedifferently.watertrackerapi.domain.userProfiles.controllers;

import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileCreateRequest;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileDTO;
import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;
import com.codedifferently.watertrackerapi.domain.userProfiles.services.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/userprofile")
public class UserProfileController {

    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("create")
    public ResponseEntity<UserProfileDTO> create (@RequestBody UserProfileCreateRequest userDetail){
        UserProfileDTO userProfile = userProfileService.create(userDetail);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<UserProfileDTO>> getAll(){
        Iterable<UserProfileDTO> userProfiles = userProfileService.getAll();
        return new ResponseEntity<>(userProfiles, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserProfileDTO> getById(@PathVariable("id") String id){
        UserProfileDTO userProfile = userProfileService.getById(id);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<UserProfileDTO> getByEmail(@PathVariable("email")String email){
        UserProfileDTO userProfile = userProfileService.getByEmail(email);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserProfileDTO> update(@PathVariable("id")String id, @RequestBody UserProfile userProfile){
        UserProfileDTO userProfileDTO = userProfileService.update(id, userProfile);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        userProfileService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}/followers")
    public ResponseEntity<Iterable<UserProfileDTO>> getFollowers(@PathVariable("id") String userId){
        Iterable<UserProfileDTO> followers = userProfileService.getFollowers(userId);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("{id}/following")
    public ResponseEntity<Iterable<UserProfileDTO>> getFollowing(@PathVariable("id") String userId){
        Iterable<UserProfileDTO> following = userProfileService.getFollowing(userId);
        return new ResponseEntity<>(following, HttpStatus.OK);
    }


    @PostMapping("follow")
    public ResponseEntity follow(@RequestBody Map<String,String> data){
        String userId = data.get("userId");
        String followerId = data.get("followerId");
        userProfileService.follow(userId, followerId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("unfollow")
    public ResponseEntity unfollow(@RequestBody Map<String,String> data){
        String userId = data.get("userId");
        String followerId = data.get("followerId");
        userProfileService.unfollow(userId, followerId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
