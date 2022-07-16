package com.wallet.wallet.services;

import com.wallet.wallet.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    Optional<User> findById(Long id);
}
