package com.codedifferently.firebaseapiauthenticationdemo.domain.users.service;

import com.codedifferently.firebaseapiauthenticationdemo.domain.users.models.UserProfile;

public interface UserService {
    UserProfile create(UserProfile userProfile);
    Boolean doesExist(String id);
}
