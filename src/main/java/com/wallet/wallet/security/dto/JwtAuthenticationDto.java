package com.wallet.wallet.security.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@SuppressWarnings("all")
@Data
public class JwtAuthenticationDto {

    @NotNull(message = "Informe um email")
    @Email
    private String email;

    @NotNull(message = "Informe uma senha")
    private String password;

}
