package com.codedifferently.watertrackerapi.domain.userProfiles.repos;

import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepo extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findByEmail(String email);
}
