package com.pages.services.enums;

public enum BonusOption {
    USE_BONUSES(true),
    NOT_USE_BONUSES(false);

    private final boolean useBonuses;

    BonusOption(boolean useBonuses) {
        this.useBonuses = useBonuses;
    }

    public boolean isUseBonuses() {
        return useBonuses;
    }
}
