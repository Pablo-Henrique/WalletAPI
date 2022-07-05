package com.wallet.wallet.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserWalletDto implements Serializable {

    private static final long serialVersionUID = -4056704019695471529L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Informe o id do usuário!")
    private Long user;

    @NotNull(message = "Informe o id da carteira!")
    private Long wallet;

}
