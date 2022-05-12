package com.wallet.wallet.responses;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Response<Type> {

    private Type data;
    private List<String> errors;

}
