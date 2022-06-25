package com.wallet.wallet.services;

import com.wallet.wallet.enums.TypeEnum;
import com.wallet.wallet.models.WalletItem;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WalletItemService {

    WalletItem save(WalletItem walletItem);

    Page<WalletItem> findBetweenDates(Long id, Date initial, Date end, int page);

    List<WalletItem> findByWalletAndType(long id, TypeEnum en);

    BigDecimal sumByWalletId(long id);

    Optional<WalletItem> findById(long id);

    void deleteById(Long id);

}
