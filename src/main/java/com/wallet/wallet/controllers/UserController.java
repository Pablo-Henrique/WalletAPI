package com.wallet.wallet.controllers;


import com.wallet.wallet.dtos.UserDto;
import com.wallet.wallet.models.UserEntity;
import com.wallet.wallet.responses.Response;
import com.wallet.wallet.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Response<UserDto>> createUser(@RequestBody @Valid UserDto userDto, BindingResult result) {
        Response<UserDto> response = new Response<>();

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
