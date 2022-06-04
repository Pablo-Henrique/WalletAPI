package com.wallet.wallet.domain.repository;

import com.wallet.wallet.domain.models.User;
import com.wallet.wallet.domain.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
        User user = new User();
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
        User user = new User();
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("15221");

        User response = userRepository.save(user);
        assertNotNull(response);
    }

    public void testFindByEmailEquals() {
        Optional<User> response = userRepository.findByEmailEquals(EMAIL);
        assertTrue(response.isPresent());
        assertEquals(response.get().getEmail(), EMAIL);
    }


}
