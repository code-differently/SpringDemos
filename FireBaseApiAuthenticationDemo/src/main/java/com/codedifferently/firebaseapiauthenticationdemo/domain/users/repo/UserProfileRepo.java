package com.codedifferently.firebaseapiauthenticationdemo.domain.users.repo;

import com.codedifferently.firebaseapiauthenticationdemo.domain.users.models.UserProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserProfileRepo extends CrudRepository<UserProfile, String> {
    Optional<UserProfile> findByEmail(String email);
}
