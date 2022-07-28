package com.codedifferently.watertrackerapi.domain.userProfiles.services;

import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceCreationException;
import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceNotFoundException;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileCreateRequest;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileDTO;
import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;


public interface UserProfileService {
    UserProfileDTO create(UserProfileCreateRequest detailDTO) throws ResourceCreationException;
    Iterable<UserProfileDTO> getAll();
    UserProfileDTO getById(String id) throws ResourceNotFoundException;
    UserProfileDTO getByEmail(String email) throws ResourceNotFoundException;
    UserProfileDTO update(String id, UserProfile userDetail) throws ResourceNotFoundException;
    void delete(String id);
    void follow(String userId, String followerId) throws ResourceNotFoundException;
    void unfollow(String userId, String followerId) throws ResourceNotFoundException;
    Iterable<UserProfileDTO> getFollowers(String id) throws ResourceNotFoundException;
}
