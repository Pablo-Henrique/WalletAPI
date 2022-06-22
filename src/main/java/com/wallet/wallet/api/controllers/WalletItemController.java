package com.wallet.wallet.api.controllers;

import com.wallet.wallet.api.dtos.WalletItemDto;
import com.wallet.wallet.api.responses.Response;
import com.wallet.wallet.domain.enums.TypeEnum;
import com.wallet.wallet.domain.models.WalletItem;
import com.wallet.wallet.domain.services.WalletItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/wallet-item")
public class WalletItemController {

    @Autowired
    private WalletItemService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<Response<WalletItemDto>> saveWalletItem(@RequestBody @Valid WalletItemDto dto, BindingResult result) {
        Response<WalletItemDto> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        WalletItem walletItem = service.save(mapper.map(dto, WalletItem.class));
        response.setData(mapper.map(walletItem, WalletItemDto.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{wallet}")
    public ResponseEntity<Response<List<WalletItemDto>>> findByWalletAndType(@PathVariable(value = "wallet") Long wallet,
                                                                             @RequestParam("Type") String type) {
        Response<List<WalletItemDto>> response = new Response<>();
        List<WalletItem> walletItems = service.findByWalletAndType(wallet, TypeEnum.getEnum(type));

        List<WalletItemDto> dto = new ArrayList<>();
        walletItems.forEach(items -> dto.add(mapper.map(items, WalletItemDto.class)));
        response.setData(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/total/{wallet}")
    public ResponseEntity<Response<BigDecimal>> sumByWalletId(@PathVariable("wallet") Long wallet) {

        Response<BigDecimal> response = new Response<>();
        BigDecimal value = service.sumByWalletId(wallet);
        response.setData(value == null ? BigDecimal.ZERO : value);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
