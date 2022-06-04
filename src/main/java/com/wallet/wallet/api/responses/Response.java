package com.wallet.wallet.api.responses;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Response<Type> {

    private Type data;
    private List<String> errors;

    public List<String> getErrors() {
        if(this.errors == null) {
            this.errors = new ArrayList<>();
        }
        return errors;
    }
}
