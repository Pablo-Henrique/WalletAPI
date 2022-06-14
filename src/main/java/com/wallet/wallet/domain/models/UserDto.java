package com.wallet.wallet.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UserDto implements Serializable {
    private final Long id;
    private final String name;
    private final String password;
    private final String email;
}
