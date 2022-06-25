package com.wallet.wallet.mappers;

import com.wallet.wallet.dtos.WalletDto;
import com.wallet.wallet.models.Wallet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Wallet converterDtoToEntity(WalletDto dto) {
        return modelMapper.map(dto, Wallet.class);
    }

    public WalletDto converterEntityToDto(Wallet entity) {
        return modelMapper.map(entity, WalletDto.class);
    }

    public WalletDto response(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setName(wallet.getName());
        walletDto.setValue(wallet.getValue());
        return walletDto;
    }
}
