package com.wallet.wallet.api.controllers;


import com.wallet.wallet.api.dtos.UserDto;
import com.wallet.wallet.api.mappers.UserMapper;
import com.wallet.wallet.api.responses.Response;
import com.wallet.wallet.domain.models.User;
import com.wallet.wallet.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @PostMapping
    public ResponseEntity<Response<UserDto>> createUser(@RequestBody @Valid UserDto userDto, BindingResult result) {
        Response<UserDto> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(errors -> response.getErrors().add(errors.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user = userService.save(mapper.converterDtoToEntity(userDto));
        response.setData(mapper.dtoResponse(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
