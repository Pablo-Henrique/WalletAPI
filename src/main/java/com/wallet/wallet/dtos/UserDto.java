package com.wallet.wallet.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8852140062261657715L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Email(message = "Email Inválido")
    private String email;

    @Length(min = 3, max = 50, message = "O nome deve conter entre 3 a 50 caracteres")
    private String name;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Length(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
    private String password;

}
