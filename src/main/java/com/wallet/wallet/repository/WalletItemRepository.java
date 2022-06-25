package com.wallet.wallet.repository;

import com.wallet.wallet.enums.TypeEnum;
import com.wallet.wallet.models.WalletItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {

    Page<WalletItem> findByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long id, Date init, Date end, Pageable pg);

    List<WalletItem> findByWalletIdAndType(Long id, TypeEnum typeEnum);

    @Query(value = "select sum(value) from WalletItem wi where wi.wallet.id = :wallet")
    BigDecimal sumByWalletId(@Param("wallet") Long wallet);
}
