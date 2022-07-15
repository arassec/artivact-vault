package com.arassec.artivact.vault.service.model;

import lombok.Getter;

public enum ImageSize {

    ORIGINAL(-1),

    CARD(300),

    CAROUSEL(1920);

    @Getter
    private int width;

    ImageSize(int width) {
        this.width = width;
    }

}
