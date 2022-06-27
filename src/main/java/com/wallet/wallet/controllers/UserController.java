package com.wallet.wallet.controllers;


import com.wallet.wallet.dtos.UserDto;
import com.wallet.wallet.mappers.UserMapper;
import com.wallet.wallet.models.User;
import com.wallet.wallet.responses.Response;
import com.wallet.wallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @PostMapping
    public ResponseEntity<Response<UserDto>> create(@RequestBody @Valid UserDto userDto, BindingResult result) {
        Response<UserDto> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(errors -> response.getErrors().add(errors.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user = userService.save(mapper.converterDtoToEntity(userDto));
        response.setData(mapper.converterEntityToDto(user));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Response<List<UserDto>>> findAll() {
        Response<List<UserDto>> response = new Response<>();
        response.setData(mapper.converterListEntityToDto(userService.findAll()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
