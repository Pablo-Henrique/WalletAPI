package com.wallet.wallet.domain.repository;

import com.wallet.wallet.domain.enums.TypeEnum;
import com.wallet.wallet.domain.models.Wallet;
import com.wallet.wallet.domain.models.WalletItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletItemsRepositoryTest {

    private static final Date DATE = new Date();
    private static final TypeEnum TYPE = TypeEnum.EN;
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);
    private Long savedWalletItemsId = null;
    private Long savedWalletId = null;

    @Autowired
    private WalletItemsRepository walletItemsRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Before
    public void setUp() {
        Wallet wallet = new Wallet("WalletForTest", BigDecimal.valueOf(250));
        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(DATE, TYPE, DESCRIPTION, VALUE, wallet);
        walletItemsRepository.save(walletItem);

        savedWalletId = wallet.getId();
        savedWalletItemsId = walletItem.getId();
    }

    @After
    public void tearDown() {
        walletItemsRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    public void testSave() {
        Wallet wallet = new Wallet("WalletForTest", BigDecimal.valueOf(500));
        assertNotNull(walletRepository.save(wallet));

        WalletItem walletItem = new WalletItem(DATE, TYPE, DESCRIPTION, VALUE, wallet);
        assertNotNull(walletItemsRepository.save(walletItem));

        assertEquals(walletItem.getType(), TYPE);
        assertEquals(walletItem.getDescription(), DESCRIPTION);
        assertEquals(walletItem.getValue(), VALUE);
        assertEquals(walletItem.getWallet().getId(), wallet.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveInvalidWalletItem() {
        WalletItem walletItem = new WalletItem(DATE, null, DESCRIPTION, null, null);
        walletItemsRepository.save(walletItem);
    }

    @Test
    public void testUpdate() {
        Optional<WalletItem> walletItem = walletItemsRepository.findById(savedWalletItemsId);
        assertNotNull(walletItem);

        String description = "Descrição alterada";

        WalletItem updatedWalletItem = walletItem.get();
        updatedWalletItem.setDescription(description);

        walletItemsRepository.save(updatedWalletItem);

        Optional<WalletItem> newWalletItem = walletItemsRepository.findById(savedWalletItemsId);

        assertEquals(newWalletItem.get().getId(), savedWalletItemsId);
        assertEquals(description, newWalletItem.get().getDescription());
    }

    @Test
    public void deleteWalletItem(){
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        WalletItem walletItem = new WalletItem(DATE, TYPE, DESCRIPTION, VALUE, wallet.get());

        walletItemsRepository.save(walletItem);
        walletItemsRepository.deleteById(walletItem.getId());

        Optional<WalletItem> response = walletItemsRepository.findById(walletItem.getId());
        assertFalse(response.isPresent());
    }
}
