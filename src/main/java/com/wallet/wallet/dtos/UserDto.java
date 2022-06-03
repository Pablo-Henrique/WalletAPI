package com.wallet.wallet.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    private Long id;

    @Email(message = "Email Inválido")
    private String email;

    @Length(min = 3, max = 50, message = "O nome deve conter entre 3 a 50 caracteres")
    private String name;

    @NotNull
    @Length(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
    private String password;

}
