package com.wallet.wallet.repository;

import com.wallet.wallet.models.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {

    Optional<UserWallet> findByUsersIdAndWalletId(Long user, Long wallet);
}
