package com.wallet.wallet.mappers;

import com.wallet.wallet.dtos.WalletItemDto;
import com.wallet.wallet.enums.TypeEnum;
import com.wallet.wallet.models.Wallet;
import com.wallet.wallet.models.WalletItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletItemMapper {

    @Autowired
    private ModelMapper modelMapper;

    public WalletItem convertDtoToEntity(WalletItemDto dto) {
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

    public WalletItemDto convertEntityToDto(WalletItem wi) {
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
