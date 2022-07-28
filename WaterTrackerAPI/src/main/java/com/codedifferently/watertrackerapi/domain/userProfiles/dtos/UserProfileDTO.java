package com.codedifferently.watertrackerapi.domain.userProfiles.dtos;

import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserProfileDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer numberOfFollowers;

    public UserProfileDTO(UserProfile userProfile){
        id = userProfile.getId();
        firstName = userProfile.getFirstName();
        lastName = userProfile.getLastName();
        email = userProfile.getEmail();
        numberOfFollowers = userProfile.getFollowers().size();
    }
}
