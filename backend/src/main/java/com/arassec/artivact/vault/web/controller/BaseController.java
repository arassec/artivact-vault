package com.arassec.artivact.vault.web.controller;

import com.arassec.artivact.vault.service.model.Artivact;
import com.arassec.artivact.vault.service.model.ImageSize;

public abstract class BaseController {

    protected String createMainImageUrl(Artivact artivact, ImageSize imageSize) {
        if (!artivact.getMediaContent().getImages().isEmpty()) {
            return createImageUrl(artivact.getId(), artivact.getMediaContent().getImages().get(0), imageSize);
        }
        return null;
    }

    protected String createImageUrl(String artivactId, String fileName, ImageSize imageSize) {
        String url = createUrl(artivactId, fileName, "image");
        if (imageSize != null) {
            url += "?imageSize=" + imageSize;
        }
        return url;
    }

    protected String createModelUrl(String artivactId, String fileName) {
        return createUrl(artivactId, fileName, "model");
    }

    protected String createUrl(String artivactId, String fileName, String fileType) {
        return "/api/artivact/media/" + artivactId + "/" + fileType + "/" + fileName;
    }
}
