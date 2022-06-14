package com.wallet.wallet.domain.services;

import com.wallet.wallet.domain.models.User;

import java.util.Optional;

public interface UserService {

    User save(User user);
    Optional<User> findByEmail(String email);
}
