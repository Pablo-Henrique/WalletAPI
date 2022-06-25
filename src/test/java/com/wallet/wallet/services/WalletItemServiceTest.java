package com.wallet.wallet.services;

import com.wallet.wallet.enums.TypeEnum;
import com.wallet.wallet.models.Wallet;
import com.wallet.wallet.models.WalletItem;
import com.wallet.wallet.repository.WalletItemRepository;
import com.wallet.wallet.services.WalletItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletItemServiceTest {

    @MockBean
    private WalletItemRepository walletItemRepository;

    @Autowired
    private WalletItemService walletItemService;

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE_ENUM = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    @Test
    public void testSave() {
        BDDMockito.given(walletItemRepository.save(Mockito.any(WalletItem.class))).willReturn(mockWalletItem());
        WalletItem walletItem = walletItemRepository.save(new WalletItem());
        assertNotNull(walletItem);
        assertEquals(walletItem.getDescription(), DESCRIPTION);
        assertEquals(walletItem.getValue().compareTo(VALUE), 0);
    }

    @Test
    public void testFindBetweenDates() {
        List<WalletItem> items = new ArrayList<>();
        items.add(mockWalletItem());
        Page<WalletItem> page = new PageImpl(items);

        BDDMockito.given(walletItemRepository.findByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(PageRequest.class))).willReturn(page);
        Page<WalletItem> response = walletItemService.findBetweenDates(1L, new Date(), new Date(), 0);
        assertNotNull(response);
        assertEquals(response.getContent().size(), 1);
        assertEquals(response.getContent().get(0).getDescription(), DESCRIPTION);
    }

    @Test
    public void testFindByType(){
        List<WalletItem> items = new ArrayList<>();
        items.add(mockWalletItem());

        BDDMockito.given(walletItemRepository.findByWalletIdAndType(Mockito.anyLong(), Mockito.any(TypeEnum.class))).willReturn(items);

        List<WalletItem> response = walletItemService.findByWalletAndType(1L, TypeEnum.EN);
        assertNotNull(response);
        assertEquals(response.get(0).getType(), TYPE_ENUM);
    }

    @Test
    public void testSumByWallet() {
        BigDecimal value = BigDecimal.valueOf(45);

        BDDMockito.given(walletItemRepository.sumByWalletId(Mockito.anyLong())).willReturn(value);
        BigDecimal response = walletItemService.sumByWalletId(1L);
        assertEquals(response.compareTo(value), 0);
    }

    private WalletItem mockWalletItem() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        return new WalletItem(DATE, TYPE_ENUM, DESCRIPTION, VALUE, wallet);
    }

}
