package com.codedifferently.watertrackerapi.security.firebase.services;

import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceCreationException;
import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceUpdateException;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileCreateRequest;
import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FirebaseUserMgrServiceImpl implements FirebaseUserMgrService{
    @Override
    public String createFireBaseUser(UserProfileCreateRequest userDetail) throws ResourceCreationException {
        try {
            String displayName = String.format("%s %s", userDetail.getFirstName(), userDetail.getLastName());
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(userDetail.getEmail())
                    .setEmailVerified(true)
                    .setPassword(userDetail.getPassword())
                    .setDisplayName(displayName)
                    .setDisabled(false);
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            return userRecord.getUid();
        } catch (FirebaseAuthException e){
            throw new ResourceCreationException(e.getMessage());
        }
    }

    @Override
    public void updateFireBaseUser(UserProfile userDetail) throws ResourceUpdateException {
        try {
            String displayName = String.format("%s %s", userDetail.getFirstName(), userDetail.getLastName());
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userDetail.getId())
                    .setEmail(userDetail.getEmail())
                    .setDisplayName(displayName);
            FirebaseAuth.getInstance().updateUser(request);
        }catch (FirebaseAuthException e){
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteFireBaseUser(String id) throws ResourceUpdateException {
        try {
            FirebaseAuth.getInstance().deleteUser(id);
        }catch (FirebaseAuthException e){
            throw new ResourceUpdateException(e.getMessage());
        }
    }
}
