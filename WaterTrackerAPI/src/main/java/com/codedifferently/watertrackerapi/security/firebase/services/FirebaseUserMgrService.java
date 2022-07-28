package com.codedifferently.watertrackerapi.security.firebase.services;

import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceCreationException;
import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceUpdateException;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileCreateRequest;
import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;

public interface FirebaseUserMgrService {
    String createFireBaseUser(UserProfileCreateRequest userDetail) throws ResourceCreationException;
    void updateFireBaseUser(UserProfile userDetail) throws ResourceUpdateException;
    void deleteFireBaseUser(String id) throws ResourceUpdateException;
}
