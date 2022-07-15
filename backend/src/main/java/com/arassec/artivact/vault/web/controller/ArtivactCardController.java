package com.arassec.artivact.vault.web.controller;

import com.arassec.artivact.vault.service.ArtivactService;
import com.arassec.artivact.vault.service.model.ImageSize;
import com.arassec.artivact.vault.web.model.ArtivactCardData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/artivact/card")
public class ArtivactCardController extends BaseController {

    private final ArtivactService artivactService;

    @GetMapping
    public List<ArtivactCardData> loadCards() {
        return artivactService.loadArtivacts().stream()
                .map(artivact -> ArtivactCardData.builder()
                        .artivactId(artivact.getId())
                        .title(artivact.getName())
                        .imageUrl(createMainImageUrl(artivact, ImageSize.CARD))
                        .build()
                )
                .collect(Collectors.toList());
    }

}
