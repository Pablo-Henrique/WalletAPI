package com.wallet.wallet.util;

import com.wallet.wallet.models.User;
import com.wallet.wallet.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestrictByUserUtil {


    private static UserService service;

    public RestrictByUserUtil(UserService service) {
        RestrictByUserUtil.service = service;
    }

    public static Long getAuthenticatedUserId() {
        try {
            Optional<User> user = service.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            return user.map(User::getId).orElse(null);

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
