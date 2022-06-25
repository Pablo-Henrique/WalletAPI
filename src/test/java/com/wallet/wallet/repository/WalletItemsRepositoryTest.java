package com.wallet.wallet.repository;

import com.wallet.wallet.enums.TypeEnum;
import com.wallet.wallet.models.Wallet;
import com.wallet.wallet.models.WalletItem;
import com.wallet.wallet.repository.WalletItemRepository;
import com.wallet.wallet.repository.WalletRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
    private Long savedWalletItemsId;
    private Long savedWalletId;

    @Autowired
    private WalletItemRepository walletItemRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Before
    public void setUp() {
        Wallet wallet = new Wallet("WalletForTest", BigDecimal.valueOf(250));
        walletRepository.save(wallet);

        WalletItem walletItem = new WalletItem(DATE, TYPE, DESCRIPTION, VALUE, wallet);
        walletItemRepository.save(walletItem);

        savedWalletId = wallet.getId();
        savedWalletItemsId = walletItem.getId();
    }

    @After
    public void tearDown() {
        walletItemRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    public void testSave() {
        Wallet wallet = new Wallet("WalletForTest", BigDecimal.valueOf(500));
        assertNotNull(walletRepository.save(wallet));

        WalletItem walletItem = new WalletItem(DATE, TYPE, DESCRIPTION, VALUE, wallet);
        assertNotNull(walletItemRepository.save(walletItem));

        assertEquals(walletItem.getType(), TYPE);
        assertEquals(walletItem.getDescription(), DESCRIPTION);
        assertEquals(walletItem.getValue(), VALUE);
        assertEquals(walletItem.getWallet().getId(), wallet.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveInvalidWalletItem() {
        WalletItem walletItem = new WalletItem(DATE, null, DESCRIPTION, null, null);
        walletItemRepository.save(walletItem);
    }

    @Test
    public void testUpdate() {
        Optional<WalletItem> walletItem = walletItemRepository.findById(savedWalletItemsId);
        assertNotNull(walletItem);

        String description = "Descrição alterada";

        WalletItem updatedWalletItem = walletItem.get();
        updatedWalletItem.setDescription(description);

        walletItemRepository.save(updatedWalletItem);

        Optional<WalletItem> newWalletItem = walletItemRepository.findById(savedWalletItemsId);

        assertEquals(newWalletItem.get().getId(), savedWalletItemsId);
        assertEquals(description, newWalletItem.get().getDescription());
    }

    @Test
    public void deleteWalletItem() {
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        WalletItem walletItem = new WalletItem(DATE, TYPE, DESCRIPTION, VALUE, wallet.get());

        walletItemRepository.save(walletItem);
        walletItemRepository.deleteById(walletItem.getId());

        Optional<WalletItem> response = walletItemRepository.findById(walletItem.getId());
        assertFalse(response.isPresent());
    }

    @Test
    public void testFindBetweenDates() {
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);

        LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
        Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        walletItemRepository.save(new WalletItem(currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE, wallet.get()));
        walletItemRepository.save(new WalletItem(currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE, wallet.get()));

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<WalletItem> response = walletItemRepository
                .findByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(savedWalletId, DATE, currentDatePlusFiveDays, pageRequest);
        assertEquals(response.getContent().size(), 2);
        assertEquals(response.getTotalElements(), 2);
        assertEquals(response.getContent().get(0).getWallet().getId(), savedWalletId);
    }

    @Test
    public void testFindByType() {
        List<WalletItem> walletItem = walletItemRepository.findByWalletIdAndType(savedWalletId, TYPE);
        assertEquals(walletItem.size(), 1);
        assertEquals(walletItem.get(0).getType(), TYPE);
    }

    @Test
    public void testFindByTypeSD() {
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
        walletItemRepository.save(new WalletItem(DATE, TypeEnum.SD, DESCRIPTION, VALUE, wallet.get()));

        List<WalletItem> walletItem = walletItemRepository.findByWalletIdAndType(savedWalletId, TypeEnum.SD);
        assertEquals(walletItem.size(), 1);
        assertEquals(walletItem.get(0).getType(), TypeEnum.SD);
    }

    @Test
    public void testSumByWallet() {
        Optional<Wallet> wallet = walletRepository.findById(savedWalletId);

        walletItemRepository.save(new WalletItem(DATE, TYPE, DESCRIPTION, BigDecimal.valueOf(150.80), wallet.get()));
        BigDecimal response = walletItemRepository.sumByWalletId(savedWalletId);
        assertEquals(response.compareTo(BigDecimal.valueOf(215.8)), 0);
    }

}
