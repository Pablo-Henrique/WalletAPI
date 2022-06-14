package com.wallet.wallet.api.dtos;

import com.wallet.wallet.domain.models.UserDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
public class UserWalletDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4056704019695471529L;

    private final Long id;

    @NotNull(message = "Informe o id do usuário!")
    private final UserDto user;

    @NotNull(message = "Informe o id da carteira!")
    private final WalletDto wallet;

}
