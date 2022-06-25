package com.wallet.wallet.mappers;

import com.wallet.wallet.dtos.UserWalletDto;
import com.wallet.wallet.models.User;
import com.wallet.wallet.models.UserWallet;
import com.wallet.wallet.models.Wallet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWalletMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserWallet converterDtoToEntity(UserWalletDto dto) {
        UserWallet userWallet = new UserWallet();

        User user = new User();
        user.setId(dto.getUser());

        Wallet wallet = new Wallet();
        wallet.setId(dto.getWallet());

        userWallet.setId(dto.getId());
        userWallet.setUser(user);
        userWallet.setWallet(wallet);

        return userWallet;
    }

    public UserWalletDto converterEntityToDto(UserWallet entity) {
        UserWalletDto userWalletDto = new UserWalletDto();
        userWalletDto.setId(entity.getId());
        userWalletDto.setUser(entity.getUser().getId());
        userWalletDto.setWallet(entity.getWallet().getId());
        return userWalletDto;
    }
}
