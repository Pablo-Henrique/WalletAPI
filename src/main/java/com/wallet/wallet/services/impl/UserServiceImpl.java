package com.wallet.wallet.services.impl;

import com.wallet.wallet.models.User;
import com.wallet.wallet.repository.UserRepository;
import com.wallet.wallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailEquals(email);
    }
}
