package com.example.gatewaymanagment.domain.common.validation;

public class NotFoundException extends DomainException {
    private String code;
    private String pointer;
    private String message;

    public NotFoundException(String code, String pointer, String message){
        this.code = code;
        this.pointer = pointer;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
