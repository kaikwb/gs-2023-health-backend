package br.com.fiap.gs2023healthbackend.exceptions;

import lombok.Getter;

@Getter
public class InvalidSignupParameter extends Exception {
    private final String errorMessage;

    public InvalidSignupParameter(String message) {
        super("%s: %s".formatted(InvalidSignupParameter.class.getSimpleName(), message));
        this.errorMessage = message;
    }
}
