package com.arassec.artivact.vault.web.controller;

import com.arassec.artivact.vault.service.ArtivactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/administration")
public class AdministrationController {

    private final ArtivactService artivactService;

    @PostMapping
    public void scanArtivactDir() {
        artivactService.scanArtivactsDir();
    }

}
