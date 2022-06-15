package com.wallet.wallet.api.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletItemDto implements Serializable {

    private Long id;

    @NotNull(message = "Informe uma data!")
    private Date date;

    @NotNull(message = "Informe um tipo")
    @Pattern(regexp = "^(ENTRADA|SAÍDA)$", message = "Para o tipo somente são aceitos ETNRADA E SAÍDA")
    private String type;

    @NotNull(message = "Informe uma descrição!")
    private String description;

    @NotNull(message = "Informe um valor!")
    private BigDecimal value;

    @NotNull(message = "Insira o id da carteira!")
    private Long wallet;
}
