package com.wallet.wallet.security.service;

import com.wallet.wallet.models.User;
import com.wallet.wallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            return JwtUserFactory.create(user.get());

        }

        throw new UsernameNotFoundException("Email não encontrado!");
    }
}
