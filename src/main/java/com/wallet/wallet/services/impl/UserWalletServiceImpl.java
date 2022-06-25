package com.wallet.wallet.services.impl;

import com.wallet.wallet.models.UserWallet;
import com.wallet.wallet.repository.UserWalletRepository;
import com.wallet.wallet.services.UserWalletService;
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
