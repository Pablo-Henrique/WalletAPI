package com.wallet.wallet.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1011142458143431939L;

    public ResourceNotFoundException(Object id) {
        super("Resource not Found. id: " + id);
    }
}
