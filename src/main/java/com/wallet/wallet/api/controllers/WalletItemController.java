package com.wallet.wallet.api.controllers;

import com.wallet.wallet.api.dtos.WalletItemDto;
import com.wallet.wallet.api.responses.Response;
import com.wallet.wallet.domain.enums.TypeEnum;
import com.wallet.wallet.domain.models.WalletItem;
import com.wallet.wallet.domain.services.WalletItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/wallet-item")
public class WalletItemController {

    @Autowired
    private WalletItemService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<Response<WalletItemDto>> create(@RequestBody @Valid WalletItemDto dto, BindingResult result) {
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
    public ResponseEntity<Response<Page<WalletItemDto>>> findBetweenDates(
            @PathVariable("wallet") Long wallet,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        Response<Page<WalletItemDto>> response = new Response<>();
        Page<WalletItem> items = service.findBetweenDates(wallet, startDate, endDate, page);
        Page<WalletItemDto> dto = items.map(i -> mapper.map(i, WalletItemDto.class));
        response.setData(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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

    @PutMapping
    public ResponseEntity<Response<WalletItemDto>> update(@RequestBody @Valid WalletItemDto dto, BindingResult result) {
        Response<WalletItemDto> response = new Response<>();
        Optional<WalletItem> walletItem = service.findById(dto.getId());

        if (walletItem.isEmpty()) {
            result.addError(new ObjectError("WalletItem", "WalletItem não encontrado"));
        } else if (walletItem.get().getWallet().getId().compareTo(dto.getWallet()) != 0) {
            result.addError(new ObjectError("WalletItemChanged", "Você não pode alterar a carteira"));
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        WalletItem newWalletItem = service.save(mapper.map(dto, WalletItem.class));
        response.setData(mapper.map(newWalletItem, WalletItemDto.class));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{walletItemId}")
    public ResponseEntity<Response<String>> delete(@PathVariable(value = "walletItemId") Long walletItemId) {
        Response<String> response = new Response<>();
        Optional<WalletItem> walletItem = service.findById(walletItemId);

        if (walletItem.isEmpty()) {
            response.getErrors().add("Carteira de id " + walletItemId + " não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        service.deleteById(walletItemId);
        response.setData("Carteira de id " + walletItemId + " apagada com sucesso");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
