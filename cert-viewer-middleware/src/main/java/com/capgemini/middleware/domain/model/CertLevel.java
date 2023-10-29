package com.capgemini.middleware.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CertLevel {
    FOUNDATION("Foundation"),
    ZERO("0"),
    ONE("1"),
    ONE_P("1P"),
    TWO("2"),
    TWO_P("2P"),
    THREE("3"),
    THREE_P("3P"),
    FOUR("4"),
    NOT_SUPPORTED("Certificate level not supported");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
