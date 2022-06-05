package com.wallet.wallet.api.dtos;

import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Getter
public class WalletDto implements Serializable {

    private final Long id;

    @Length(min = 3)
    @NotNull
    private final String name;

    @NotNull
    private final BigDecimal value;

}
