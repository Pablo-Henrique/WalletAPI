package com.wallet.wallet.domain.repository;

import com.wallet.wallet.domain.models.Wallet;
import com.wallet.wallet.domain.models.WalletItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class WalletItemsRepositoryTest {

    private static final Date DATE = new Date();
    private static final String TYPE = "ENTRADA";
    private static final String DESCRIPTION = "Conta de Luz";
    private static final BigDecimal VALUE = BigDecimal.valueOf(65);

    @Autowired
    private WalletItemsRepository repository;

    @Test
    public void testSave() {
        Wallet wallet = new Wallet();
        wallet.setName("WalletForTest");
        wallet.setValue(BigDecimal.valueOf(500));

        WalletItem walletItem = new WalletItem();

    }
}
