package com.codedifferently.watertrackerapi.domain.userProfiles.services;

import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceCreationException;
import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceNotFoundException;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileCreateRequest;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileDTO;
import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;
import com.codedifferently.watertrackerapi.domain.userProfiles.repos.UserProfileRepo;
import com.codedifferently.watertrackerapi.security.firebase.services.FirebaseUserMgrService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserProfileServiceTest {

    @MockBean
    private UserProfileRepo userProfileRepo;

    @MockBean
    private FirebaseUserMgrService firebaseUserMgrService;

    @Autowired
    private UserProfileService userProfileService;
    private UserProfileCreateRequest mockDetail;
    private UserProfile mockUserProfile;
    private String expectedId;

    @BeforeEach
    public void setUp(){
        expectedId = "xyz1234";
        mockDetail = new UserProfileCreateRequest("Test", "User","test@user.com", "password");
        mockUserProfile = new UserProfile("Test","User","test@user.com");
        mockUserProfile.setId("xyz1234");
    }

    @Test
    public void createUserProfileTest01(){
        BDDMockito.doReturn(Optional.empty()).when(userProfileRepo).findByEmail(any());
        BDDMockito.doReturn(expectedId).when(firebaseUserMgrService).createFireBaseUser(any());
        BDDMockito.doReturn(mockUserProfile).when(userProfileRepo).save(any());
        UserProfileDTO userProfile = userProfileService.create(mockDetail);
        Assertions.assertEquals(expectedId,userProfile.getId());
    }

    @Test
    public void createUserProfileTest02(){
        BDDMockito.doReturn(Optional.of(mockUserProfile)).when(userProfileRepo).findByEmail(any());
        Assertions.assertThrows(ResourceCreationException.class, ()->{
            userProfileService.create(mockDetail);
        });
    }

    @Test
    public void getByIdTest01(){
        BDDMockito.doReturn(Optional.of(mockUserProfile)).when(userProfileRepo).findById(any());
        UserProfileDTO userProfile = userProfileService.getById("xyz1234");
        Assertions.assertEquals(expectedId, userProfile.getId());
    }

    @Test
    public void getByIdTest02(){
        BDDMockito.doReturn(Optional.empty()).when(userProfileRepo).findById(any());
        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            userProfileService.getById(expectedId);
        });
    }
}
