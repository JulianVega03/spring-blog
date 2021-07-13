package com.juliandev.techie.springblog.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String mensaje) {
        super(mensaje);
    }
}
