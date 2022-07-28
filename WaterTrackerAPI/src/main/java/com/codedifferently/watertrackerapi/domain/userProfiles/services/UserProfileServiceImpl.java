package com.codedifferently.watertrackerapi.domain.userProfiles.services;

import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceCreationException;
import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceNotFoundException;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileCreateRequest;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileDTO;
import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;
import com.codedifferently.watertrackerapi.domain.userProfiles.repos.UserProfileRepo;
import com.codedifferently.watertrackerapi.security.firebase.services.FirebaseUserMgrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService{
    private UserProfileRepo userProfileRepo;
    private FirebaseUserMgrService firebaseUserMgrService;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepo userProfileRepo,
                                  FirebaseUserMgrService firebaseUserMgrService) {
        this.userProfileRepo = userProfileRepo;
        this.firebaseUserMgrService = firebaseUserMgrService;
    }

    @Override
    public UserProfileDTO create(UserProfileCreateRequest detailDTO) throws ResourceCreationException {
        Optional<UserProfile> optional = userProfileRepo.findByEmail(detailDTO.getEmail());
        if(optional.isPresent())
            throw new ResourceCreationException("User exists");
        log.info(detailDTO.toString());
        String uid= firebaseUserMgrService.createFireBaseUser(detailDTO);
        UserProfile userProfile = new UserProfile(detailDTO.getFirstName(), detailDTO.getLastName(), detailDTO.getEmail());
        userProfile.setId(uid);
        userProfile = userProfileRepo.save(userProfile);
        log.debug("Created User with id {} and email {}", userProfile.getId(), userProfile.getEmail());
        return new UserProfileDTO(userProfile);
    }

    @Override
    public Iterable<UserProfileDTO> getAll() {
        Iterable<UserProfileDTO> userProfileDTOS = convertToDtoList(userProfileRepo.findAll());
        return userProfileDTOS;
    }

    @Override
    public UserProfileDTO getById(String id) throws ResourceNotFoundException {
        UserProfile userProfile = userProfileRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(""));
        return new UserProfileDTO(userProfile);
    }

    @Override
    public UserProfileDTO getByEmail(String email) throws ResourceNotFoundException {
        UserProfile userProfile = userProfileRepo.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException(""));
        return new UserProfileDTO(userProfile);
    }

    @Override
    public UserProfileDTO update(String id, UserProfile userDetail) throws ResourceNotFoundException {
        UserProfile userProfile = retrieveById(userDetail.getId());
        firebaseUserMgrService.updateFireBaseUser(userDetail);
        userProfile.setEmail(userDetail.getEmail());
        userProfile.setFirstName(userDetail.getFirstName());
        userProfile.setLastName(userDetail.getLastName());
        userProfile = userProfileRepo.save(userProfile);
        return new UserProfileDTO(userProfile);
    }

    @Override
    public void delete(String id) {
        UserProfile userProfile = retrieveById(id);
        firebaseUserMgrService.deleteFireBaseUser(id);
        userProfileRepo.delete(userProfile);
    }

    @Override
    public void follow(String userId, String followerId) throws ResourceNotFoundException {
        UserProfile user = retrieveById(userId);
        UserProfile follower = retrieveById(followerId);
        user.addFollower(follower);
        List<UserProfile> users = new ArrayList<>();
        users.add(user);
        users.add(follower);
        userProfileRepo.saveAll(users);
    }

    @Override
    public void unfollow(String userId, String followerId) throws ResourceNotFoundException {
        UserProfile user = retrieveById(userId);
        UserProfile follower = retrieveById(followerId);
        user.removeFollower(follower);
        List<UserProfile> users = new ArrayList<>();
        users.add(user);
        users.add(follower);
        userProfileRepo.saveAll(users);
    }

    @Override
    public Iterable<UserProfileDTO> getFollowers(String id) throws ResourceNotFoundException {
        UserProfile user = retrieveById(id);
        Set<UserProfile> followers = user.getFollowers();
        return convertToDtoList(followers);
    }

    @Override
    public Iterable<UserProfileDTO> getFollowing(String id) throws ResourceNotFoundException {
        UserProfile user = retrieveById(id);
        Set<UserProfile> following = user.getFollowing();
        return convertToDtoList(following);
    }

    private Iterable<UserProfileDTO> convertToDtoList(Iterable<UserProfile> profiles){
        List<UserProfileDTO> userProfileDTOS = new ArrayList<>();
        for (UserProfile userProfile:profiles) {
            userProfileDTOS.add(new UserProfileDTO(userProfile));
        }
        return userProfileDTOS;
    }
    private UserProfile retrieveById(String id) throws ResourceNotFoundException {
        UserProfile userProfile = userProfileRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(""));
        return userProfile;
    }


}
