package com.wallet.wallet.domain.services;

import com.wallet.wallet.domain.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    User save(User user);
    Optional<User> findByEmail(String email);
}
