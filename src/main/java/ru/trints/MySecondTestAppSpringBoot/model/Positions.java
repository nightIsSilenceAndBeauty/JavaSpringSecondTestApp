package ru.trints.MySecondTestAppSpringBoot.model;

import lombok.Getter;

@Getter
public enum Positions {
    DEV(2.2, false),
    HR(1.2, false),
    TL(2.6, true),
    PO(2.6, true),
    TPM(2.6, true),
    CTO(2.6, true);

    private final double positionCoefficient;
    private final boolean isManager;

    Positions(double positionCoefficient, boolean isManager) {
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }
}
