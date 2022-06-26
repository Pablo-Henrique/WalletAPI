package com.wallet.wallet.controllers;

import com.wallet.wallet.dtos.WalletDto;
import com.wallet.wallet.mappers.WalletMapper;
import com.wallet.wallet.models.Wallet;
import com.wallet.wallet.responses.Response;
import com.wallet.wallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletMapper mapper;

    @PostMapping
    public ResponseEntity<Response<WalletDto>> createWallet(@RequestBody @Valid WalletDto walletDto, BindingResult result) {
        Response<WalletDto> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(errors -> response.getErrors().add(errors.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Wallet wallet = walletService.save(mapper.converterDtoToEntity(walletDto));
        response.setData(mapper.converterEntityToDto(wallet));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<WalletDto>>> findAll() {
        Response<List<WalletDto>> response = new Response<>();

        List<WalletDto> wallets = mapper.listConveterEntityToDto(walletService.findAll());
        response.setData(wallets);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
