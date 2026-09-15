package com.tts.app.exception;

public class InvalidTtsRequestException
        extends RuntimeException {


    public InvalidTtsRequestException(
            String message
    ) {

        super(message);

    }
}