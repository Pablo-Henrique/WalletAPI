package com.wallet.wallet.controllers;

import com.wallet.wallet.dtos.WalletItemDto;
import com.wallet.wallet.enums.TypeEnum;
import com.wallet.wallet.models.UserWallet;
import com.wallet.wallet.models.Wallet;
import com.wallet.wallet.models.WalletItem;
import com.wallet.wallet.responses.Response;
import com.wallet.wallet.services.UserWalletService;
import com.wallet.wallet.services.WalletItemService;
import com.wallet.wallet.util.RestrictByUserUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private WalletItemService walletItemService;

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private ModelMapper mapper;

    private static final Logger log = LoggerFactory.getLogger(WalletItemController.class);

    @PostMapping
    public ResponseEntity<Response<WalletItemDto>> create(@RequestBody @Valid WalletItemDto dto, BindingResult result) {
        Response<WalletItemDto> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        WalletItem walletItem = walletItemService.save(convertDtoToEntity(dto));
        response.setData(convertEntityToDto(walletItem));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{wallet}")
    public ResponseEntity<Response<Page<WalletItemDto>>> findBetweenDates(
            @PathVariable("wallet") Long wallet,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        Response<Page<WalletItemDto>> response = new Response<>();

        Optional<UserWallet> userWallet = userWalletService.findByUserIdAndWalletId(RestrictByUserUtil.getAuthenticatedUserId(), wallet);

        if (!userWallet.isPresent()) {
            response.getErrors().add("Você não tem acesso a essa carteira");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Page<WalletItem> items = walletItemService.findBetweenDates(wallet, startDate, endDate, page);
        Page<WalletItemDto> dto = items.map(this::convertEntityToDto);

        response.setData(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/type/{wallet}")
    public ResponseEntity<Response<List<WalletItemDto>>> findByWalletAndType(@PathVariable(value = "wallet") Long wallet,
                                                                             @RequestParam("type") String type) {

        log.info("Buscando por carteira {} e tipo {}", wallet, type);

        Response<List<WalletItemDto>> response = new Response<>();
        List<WalletItem> walletItems = walletItemService.findByWalletAndType(wallet, TypeEnum.getEnum(type));

        List<WalletItemDto> dto = new ArrayList<>();
        walletItems.forEach(items -> dto.add(convertEntityToDto(items)));

        response.setData(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/total/{wallet}")
    public ResponseEntity<Response<BigDecimal>> sumByWalletId(@PathVariable("wallet") Long wallet) {

        Response<BigDecimal> response = new Response<>();
        BigDecimal value = walletItemService.sumByWalletId(wallet);
        response.setData(value == null ? BigDecimal.ZERO : value);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<Response<WalletItemDto>> update(@RequestBody @Valid WalletItemDto dto, BindingResult result) {
        Response<WalletItemDto> response = new Response<>();
        Optional<WalletItem> walletItem = walletItemService.findById(dto.getId());

        if (!walletItem.isPresent()) {
            result.addError(new ObjectError("WalletItem", "WalletItem nao encontrado"));
        } else if (walletItem.get().getWallet().getId().compareTo(dto.getWallet()) != 0) {
            result.addError(new ObjectError("WalletItemChanged", "Você não pode alterar a carteira"));
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        WalletItem newWalletItem = walletItemService.save(convertDtoToEntity(dto));
        response.setData(convertEntityToDto(newWalletItem));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{walletItemId}")
    public ResponseEntity<Response<String>> delete(@PathVariable(value = "walletItemId") Long walletItemId) {
        Response<String> response = new Response<>();
        Optional<WalletItem> walletItem = walletItemService.findById(walletItemId);

        if (!walletItem.isPresent()) {
            response.getErrors().add("WalletItem de id " + walletItemId + " nao encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        walletItemService.deleteById(walletItemId);
        response.setData("WalletItem de id " + walletItemId + " apagada com sucesso");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    private WalletItem convertDtoToEntity(WalletItemDto dto) {
        WalletItem wi = new WalletItem();
        wi.setDate(dto.getDate());
        wi.setDescription(dto.getDescription());
        wi.setId(dto.getId());
        wi.setType(TypeEnum.getEnum(dto.getType()));
        wi.setValue(dto.getValue());

        Wallet w = new Wallet();
        w.setId(dto.getWallet());
        wi.setWallet(w);

        return wi;
    }

    private WalletItemDto convertEntityToDto(WalletItem wi) {
        WalletItemDto dto = new WalletItemDto();
        dto.setDate(wi.getDate());
        dto.setDescription(wi.getDescription());
        dto.setId(wi.getId());
        dto.setType(wi.getType().getValue());
        dto.setValue(wi.getValue());
        dto.setWallet(wi.getWallet().getId());

        return dto;
    }
}
