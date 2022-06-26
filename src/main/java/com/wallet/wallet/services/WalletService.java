package com.wallet.wallet.services;

import com.wallet.wallet.models.Wallet;

import java.util.List;

public interface WalletService {

    Wallet save(Wallet wallet);

    List<Wallet> findAll();
}
