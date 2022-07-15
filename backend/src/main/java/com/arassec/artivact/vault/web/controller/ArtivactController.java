package com.arassec.artivact.vault.web.controller;

import com.arassec.artivact.vault.service.ArtivactService;
import com.arassec.artivact.vault.service.model.ImageSize;
import com.arassec.artivact.vault.web.model.ArtivactDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/artivact")
public class ArtivactController extends BaseController {

    private final ArtivactService artivactService;

    @GetMapping(value = "/{artivactId}")
    public ResponseEntity<ArtivactDetails> loacArtivact(@PathVariable String artivactId) {
        var artivact = artivactService.loadArtivact(artivactId);
        if (artivact != null) {
            return ResponseEntity.ok(ArtivactDetails.builder()
                    .id(artivact.getId())
                    .version(artivact.getVersion())
                    .name(artivact.getName())
                    .imageUrls(artivact.getMediaContent().getImages().stream()
                            .map(fileName -> createImageUrl(artivactId, fileName, ImageSize.CAROUSEL))
                            .collect(Collectors.toList()))
                    .modelUrls(artivact.getMediaContent().getModels().stream()
                            .map(fileName -> createModelUrl(artivactId, fileName))
                            .collect(Collectors.toList()))
                    .build());
        }
        return ResponseEntity.notFound().build();
    }

}
