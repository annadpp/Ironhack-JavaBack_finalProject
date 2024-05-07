package com.ironhack.locmgmt.util;

import com.ironhack.locmgmt.model.users.Linguist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class SecurityUtil {
    public Linguist getCurrentLinguist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        return (Linguist) authentication.getPrincipal();
    }
}