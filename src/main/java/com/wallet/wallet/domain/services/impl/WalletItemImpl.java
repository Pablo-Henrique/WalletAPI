package com.wallet.wallet.domain.services.impl;

import com.wallet.wallet.domain.enums.TypeEnum;
import com.wallet.wallet.domain.models.WalletItem;
import com.wallet.wallet.domain.repository.WalletItemRepository;
import com.wallet.wallet.domain.services.WalletItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WalletItemImpl implements WalletItemService {

    @Autowired
    private WalletItemRepository walletItemRepository;

    @Value("${pagination.items_per_page}")
    private Integer itemsPerPage;

    @Override
    public WalletItem save(WalletItem walletItem) {
        return walletItemRepository.save(walletItem);
    }

    @Override
    public Page<WalletItem> findBetweenDates(Long id, Date initial, Date end, int page) {
        PageRequest pageRequest = PageRequest.of(page, itemsPerPage);
        return walletItemRepository.findByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(id, initial, end, pageRequest);
    }

    @Override
    public List<WalletItem> findByWalletAndType(long id, TypeEnum en) {
        return walletItemRepository.findByWalletIdAndType(id, en);
    }

    @Override
    public BigDecimal sumByWalletId(long id) {
        return walletItemRepository.sumByWalletId(id);
    }

    @Override
    public Optional<WalletItem> findById(long id) {
        return walletItemRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        walletItemRepository.deleteById(id);
    }
}
