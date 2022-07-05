package com.wallet.wallet.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WalletDto implements Serializable {

    private static final long serialVersionUID = -5751476943684913226L;

    private Long id;

    @NotNull(message = "O nome não deve ser nulo")
    @Length(min = 3, message = "O nome deve conter no mínimo 3 caracteres!")
    private String name;

    @NotNull(message = "Insira um valor para a carteira!")
    private BigDecimal value;

}
