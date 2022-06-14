package com.wallet.wallet.domain.services.impl;

import com.wallet.wallet.domain.models.UserWallet;
import com.wallet.wallet.domain.repository.UserWalletRepository;
import com.wallet.wallet.domain.services.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl implements UserWalletService {

    @Autowired
    private UserWalletRepository repository;

    @Override
    public UserWallet save(UserWallet userWallet) {
        return repository.save(userWallet);
    }

}
