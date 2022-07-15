package com.arassec.artivact.vault.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Artivact {

    private String id;

    private Integer version;

    private String name;

    private MediaContent mediaContent;

}
