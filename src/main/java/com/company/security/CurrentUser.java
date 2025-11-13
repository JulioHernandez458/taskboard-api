package com.company.security;

import com.company.user.User;
import com.company.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    private final UserRepository users;

    public CurrentUser(UserRepository users) {
        this.users = users;
    }

    public String email() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalStateException("No authenticated user");
        }
        return auth.getName();
    }

    public Long id() {
        String email = email();
        User u = users.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found: " + email));
        return u.getId();
    }
}
