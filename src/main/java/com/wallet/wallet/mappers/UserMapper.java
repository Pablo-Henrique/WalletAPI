package com.wallet.wallet.mappers;

import com.wallet.wallet.dtos.UserDto;
import com.wallet.wallet.enums.RoleEnum;
import com.wallet.wallet.models.User;
import com.wallet.wallet.util.Bcrypt;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User converterDtoToEntity(UserDto dto) {
        dto.setPassword(Bcrypt.getHash(dto.getPassword()));
        dto.setRole((dto.getRole()) == null ? RoleEnum.ROLE_DEFAULT.toString() : dto.getRole());
        return modelMapper.map(dto, User.class);
    }

    public UserDto converterEntityToDto(User entity) {
        return modelMapper.map(entity, UserDto.class);
    }

    public List<UserDto> converterListEntityToDto(List<User> entityList) {
        List<UserDto> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(converterEntityToDto(entity)));
        return dtoList;
    }

}
