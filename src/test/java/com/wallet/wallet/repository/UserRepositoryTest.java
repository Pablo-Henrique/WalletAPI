package com.wallet.wallet.repository;

import com.wallet.wallet.models.UserEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static final String EMAIL = "email@test.com";

    @Before
    public void setUp() throws Exception {
        UserEntity user = new UserEntity();
        user.setName("Set up User");
        user.setPassword("Senha123");
        user.setEmail(EMAIL);
        userRepository.save(user);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testSave() {
        UserEntity user = new UserEntity();
        user.setName("Teste");
        user.setEmail(EMAIL);
        user.setPassword("15221");

        UserEntity response = userRepository.save(user);

        assertNotNull(response);
    }


    public void testFindByEmail() {
        Optional<UserEntity> response = userRepository.findByEmail(EMAIL);
        assertTrue(response.isPresent());
        assertEquals(response.get().getEmail(), EMAIL);
    }


}
