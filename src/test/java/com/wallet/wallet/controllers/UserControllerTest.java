package com.wallet.wallet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.wallet.dtos.UserDto;
import com.wallet.wallet.models.UserEntity;
import com.wallet.wallet.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class UserControllerTest {


    private static final String EMAIL = "email@test.com";
    private static final String NAME = "USER TEST";
    private static final String PASSWORD = "TEST123";
    private static final String URL = "/user";

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    public void testSave() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayLoad())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    public UserEntity getMockUser() {
        UserEntity user = new UserEntity();
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(PASSWORD);
        return user;
    }

    public String getJsonPayLoad() throws JsonProcessingException {
        UserDto dto = new UserDto();
        dto.setEmail(EMAIL);
        dto.setName(NAME);
        dto.setPassword(PASSWORD);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }


}
