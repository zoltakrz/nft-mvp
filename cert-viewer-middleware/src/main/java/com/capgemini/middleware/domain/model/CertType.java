package com.capgemini.middleware.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CertType {
    ARCHITECT("Architect"),
    ENGAGEMENT_MANAGEMENT("Engagement Management"),
    NOT_SUPPORTED("Certificate type not supported");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
