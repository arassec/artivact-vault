package com.arassec.artivact.vault.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtivactCardData {

    private String artivactId;

    private String title;

    private String imageUrl;

}
