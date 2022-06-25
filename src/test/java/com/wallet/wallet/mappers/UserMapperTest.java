package com.wallet.wallet.mappers;

import com.wallet.wallet.dtos.UserDto;
import com.wallet.wallet.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class UserMapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void converterDtoTest() {
        User user = new User();
        UserDto userDto = modelMapper.map(user, UserDto.class);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPassword(), userDto.getPassword());
    }

    @Test
    public void converterEntityTest() {
        UserDto userDto = new UserDto();
        User user = modelMapper.map(userDto, User.class);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());
    }
}
