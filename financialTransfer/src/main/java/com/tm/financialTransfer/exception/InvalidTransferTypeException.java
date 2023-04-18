package com.tm.financialTransfer.exception;

public class InvalidTransferTypeException extends RuntimeException {
    public InvalidTransferTypeException(String message) {
        super(message);
    }
}
