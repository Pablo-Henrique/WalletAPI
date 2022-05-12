package com.wallet.wallet.services;

import com.wallet.wallet.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    UserEntity save(UserEntity user);
    Optional<UserEntity> findByEmail(String email);
}
