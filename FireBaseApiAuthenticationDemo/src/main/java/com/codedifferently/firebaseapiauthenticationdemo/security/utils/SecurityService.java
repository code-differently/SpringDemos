package com.codedifferently.firebaseapiauthenticationdemo.security.utils;

import com.codedifferently.firebaseapiauthenticationdemo.security.models.SecurityProperties;
import com.google.auth.Credentials;
import com.google.firebase.remoteconfig.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityService {
    private HttpServletRequest httpServletRequest;
    private CookieUtils cookieUtils;
    private SecurityProperties securityProps;

    @Autowired
    public SecurityService(HttpServletRequest httpServletRequest, CookieUtils cookieUtils, SecurityProperties securityProps) {
        this.httpServletRequest = httpServletRequest;
        this.cookieUtils = cookieUtils;
        this.securityProps = securityProps;
    }

    public User getUser() {
        User userPrincipal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof User) {
            userPrincipal = ((User) principal);
        }
        return userPrincipal;
    }

    public Credentials getCredentials() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return (Credentials) securityContext.getAuthentication().getCredentials();
    }

    public boolean isPublic() {
        return securityProps.getAllowedPublicApis().contains(httpServletRequest.getRequestURI());
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }
}
