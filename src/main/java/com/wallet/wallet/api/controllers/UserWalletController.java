package com.wallet.wallet.api.controllers;

import com.wallet.wallet.api.dtos.UserWalletDto;
import com.wallet.wallet.api.mappers.UserWalletMapper;
import com.wallet.wallet.api.responses.Response;
import com.wallet.wallet.domain.models.UserWallet;
import com.wallet.wallet.domain.services.UserWalletService;
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
@RequestMapping(path = "/userwallet")
public class UserWalletController {

    @Autowired
    private UserWalletService service;

    @Autowired
    private UserWalletMapper mapper;

    @PostMapping
    public ResponseEntity<Response<UserWalletDto>> create(@RequestBody @Valid UserWalletDto dto, BindingResult result) {
        Response<UserWalletDto> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        UserWallet userWallet = service.save(mapper.converterDtoToEntity(dto));
        response.setData(mapper.converterEntityToDto(userWallet));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
