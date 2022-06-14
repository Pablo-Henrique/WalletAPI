package com.wallet.wallet.domain.repository;

import com.wallet.wallet.domain.models.WalletItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletItemsRepository extends JpaRepository<WalletItem, Long> {
}
