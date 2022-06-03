package com.wallet.wallet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.wallet.dtos.UserDto;
import com.wallet.wallet.models.User;
import com.wallet.wallet.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class UserControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "USER TEST";
    private static final String EMAIL = "email@test.com";
    private static final String PASSWORD = "123456";
    private static final String URL = "/user";

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    public void testSave() throws Exception {

        BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockUser());

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayLoad(ID, NAME, PASSWORD, EMAIL))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").doesNotExist())
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    public void testSaveInvalidUser() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayLoad(ID, NAME, PASSWORD, "notEMAIL"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Email Inválido"));

    }

    public User getMockUser() {
        User user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(PASSWORD);
        return user;
    }

    public String getJsonPayLoad(Long id, String name, String password, String email) throws JsonProcessingException {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setName(name);
        dto.setPassword(password);
        dto.setEmail(email);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
