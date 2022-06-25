package com.wallet.wallet.services.impl;

import com.wallet.wallet.models.Wallet;
import com.wallet.wallet.repository.WalletRepository;
import com.wallet.wallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository repository;

    @Override
    @Transactional
    public Wallet save(Wallet wallet) {
        return repository.save(wallet);
    }
}
