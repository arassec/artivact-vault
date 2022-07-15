package com.arassec.artivact.vault.web.controller;

import com.arassec.artivact.vault.service.MediaService;
import com.arassec.artivact.vault.service.model.ImageSize;
import com.arassec.artivact.vault.util.ArtivactVaultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/artivact/media")
public class ArtivactMediaController {

    private final MediaService mediaService;

    @GetMapping(value = "/{artivactId}/image/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getArtivactImage(@PathVariable String artivactId, @PathVariable String fileName,
                                                   @RequestParam(required = false) ImageSize imageSize) {
        if (imageSize == null) {
            imageSize = ImageSize.ORIGINAL;
        }
        byte[] image = mediaService.getArtivactImage(artivactId, fileName, imageSize);
        var responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.valueOf(URLConnection.guessContentTypeFromName(fileName)));
        return new ResponseEntity<>(image, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/{artivactId}/model/{fileName}", produces = "model/gltf-binary")
    public HttpEntity<byte[]> getArtivactModel(@PathVariable String artivactId, @PathVariable String fileName) {
        var contentDisposition = ContentDisposition.builder("inline")
                .filename(fileName)
                .build();

        var headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        FileSystemResource model = mediaService.getArtivactModel(artivactId, fileName);

        try {
            return new HttpEntity<>(Files.readAllBytes(model.getFile().toPath()), headers);
        } catch (IOException e) {
            throw new ArtivactVaultException("Could not read artivact model!", e);
        }
    }

}
