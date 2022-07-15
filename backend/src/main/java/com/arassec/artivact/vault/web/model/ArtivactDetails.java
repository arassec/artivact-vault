package com.arassec.artivact.vault.web.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ArtivactDetails {

    private String id;

    private Integer version;

    private String name;

    private List<String> imageUrls;

    private List<String> modelUrls;

}
