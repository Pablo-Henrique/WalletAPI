package com.wallet.wallet.services;

import com.wallet.wallet.models.User;

import java.util.Optional;

public interface UserService {

    User save(User user);
    Optional<User> findByEmail(String email);
}
