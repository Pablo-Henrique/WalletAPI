package com.wallet.wallet.api.mappers;

import com.wallet.wallet.api.dtos.UserDto;
import com.wallet.wallet.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    @Autowired
    private final ModelMapper modelMapper;

    public User converterDtoToEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserDto converterEntityToDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    public UserDto dtoResponse(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}