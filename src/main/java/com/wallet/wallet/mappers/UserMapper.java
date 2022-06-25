package com.wallet.wallet.mappers;

import com.wallet.wallet.dtos.UserDto;
import com.wallet.wallet.models.User;
import com.wallet.wallet.util.Bcrypt;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User converterDtoToEntity(UserDto dto) {
        dto.setPassword(Bcrypt.getHash(dto.getPassword()));
        return modelMapper.map(dto, User.class);
    }

    public UserDto converterEntityToDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    public UserDto response(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
