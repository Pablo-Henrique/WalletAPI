package com.wallet.wallet.services;

import com.wallet.wallet.models.UserWallet;

import java.util.Optional;

public interface UserWalletService {

    UserWallet save(UserWallet userWallet);

    Optional<UserWallet> findByUsersIdAndWalletId(Long userId, Long walletId);
}
