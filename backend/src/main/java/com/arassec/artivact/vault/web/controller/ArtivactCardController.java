package com.arassec.artivact.vault.web.controller;

import com.arassec.artivact.vault.web.controller.model.ArtivactCardData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/artivact/card")
public class ArtivactCardController {

    @GetMapping
    public List<ArtivactCardData> loadCards() {
        return List.of(
                ArtivactCardData.builder().title("TEST A").build(),
                ArtivactCardData.builder().title("TEST B").build(),
                ArtivactCardData.builder().build()
        );
    }

}
