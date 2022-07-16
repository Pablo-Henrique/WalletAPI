package com.wallet.wallet.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class HandlerError implements Serializable {

    private static final long serialVersionUID = 1217464649623195292L;

    private String path;
    private Integer status;
    private String error;
    private Instant timestamp;
    private String message;

}
