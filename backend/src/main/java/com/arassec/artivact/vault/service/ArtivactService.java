package com.arassec.artivact.vault.service;

import com.arassec.artivact.vault.persistence.model.ArtivactEntity;
import com.arassec.artivact.vault.persistence.repository.ArtivactEntityRepository;
import com.arassec.artivact.vault.service.model.Artivact;
import com.arassec.artivact.vault.service.model.MediaContent;
import com.arassec.artivact.vault.util.ArtivactVaultException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtivactService {

    private final ArtivactEntityRepository artivactEntityRepository;

    private final ObjectMapper objectMapper;

    @Value("${artivacts.dir}")
    private String artivactsDir;

    @Transactional
    public void scanArtivactsDir() {
        var now = LocalDateTime.now();
        scanArtivactsDirRecursively(Path.of(artivactsDir), now);
        artivactEntityRepository.deleteAllByScannedBefore(now.minusMinutes(1));
    }

    public Artivact loadArtivact(String artivactId) {
        Optional<ArtivactEntity> entityOptional = artivactEntityRepository.findById(artivactId);
        if (entityOptional.isPresent()) {
            ArtivactEntity entity = entityOptional.get();
            try {
                return Artivact.builder()
                        .id(entity.getId())
                        .version(entity.getVersion())
                        .name(entity.getName())
                        .mediaContent(objectMapper.readValue(entity.getMediaContentJson(), MediaContent.class))
                        .build();
            } catch (JsonProcessingException e) {
                throw new ArtivactVaultException("Could not deserialize artivact's media content!", e);
            }
        }
        return null;
    }

    public List<Artivact> loadArtivacts() {
        List<Artivact> result = new LinkedList<>();
        artivactEntityRepository.findAll().forEach(entity -> {
            try {
                result.add(Artivact.builder()
                        .id(entity.getId())
                        .version(entity.getVersion())
                        .name(entity.getName())
                        .mediaContent(objectMapper.readValue(entity.getMediaContentJson(), MediaContent.class))
                        .build()
                );
            } catch (JsonProcessingException e) {
                throw new ArtivactVaultException("Could not deserialize artivact's media content!", e);
            }
        });
        return result;
    }

    public void saveArtivact(Artivact artivact) {
        var entity = new ArtivactEntity();
        entity.setId(artivact.getId());
        entity.setVersion(artivact.getVersion());
        entity.setName(artivact.getName());
        try {
            entity.setMediaContentJson(objectMapper.writeValueAsString(artivact.getMediaContent()));
        } catch (JsonProcessingException e) {
            throw new ArtivactVaultException("Could not serialize artivact's media content!", e);
        }
        artivactEntityRepository.save(entity);
    }

    private void scanArtivactsDirRecursively(Path root, LocalDateTime scanTime) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(root)) {
            directoryStream.forEach(path -> {

                if (path.getFileName().toString().equals("data.json")) {
                    log.debug("Found: {}", path.toAbsolutePath());

                    var artivactId = path.getParent().getFileName().toString();

                    log.debug("Checking artivact with ID: {}", artivactId);
                    Optional<ArtivactEntity> entityOptional = artivactEntityRepository.findById(artivactId);
                    ArtivactEntity entity;
                    if (entityOptional.isPresent()) {
                        log.debug("Found artivact, updating media content.");
                        entity = entityOptional.get();
                    } else {
                        log.debug("New artivact, saving data.");
                        entity = new ArtivactEntity();
                        entity.setId(artivactId);
                        entity.setVersion(0);
                        entity.setName("");
                    }

                    entity.setScanned(scanTime);

                    var mediaContent = createMediaContent(path.getParent());
                    try {
                        entity.setMediaContentJson(objectMapper.writeValueAsString(mediaContent));
                    } catch (JsonProcessingException e) {
                        throw new ArtivactVaultException("Could not serialize artivact's media content during scan!", e);
                    }

                    artivactEntityRepository.save(entity);
                } else if (Files.isDirectory(path)) {
                    scanArtivactsDirRecursively(path, scanTime);
                }

            });
        } catch (IOException e) {
            throw new ArtivactVaultException("Could not scan artivacts directory!", e);
        }
    }

    private MediaContent createMediaContent(Path artivactDir) {
        var result = new MediaContent();

        var imagesDir = Path.of(artivactDir.toString(), "images");
        if (Files.exists(imagesDir)) {
            try (Stream<Path> files = Files.list(imagesDir)) {
                result.setImages(files
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList())
                );
            } catch (IOException e) {
                throw new ArtivactVaultException("Could not scan images of artivact: " + artivactDir, e);
            }
        }

        var modelsDir = Path.of(artivactDir.toString(), "models");
        if (Files.exists(modelsDir)) {
            try (Stream<Path> files = Files.list(modelsDir)) {
                result.setModels(files
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList())
                );
            } catch (IOException e) {
                throw new ArtivactVaultException("Could not scan images of artivact: " + artivactDir, e);
            }
        }

        return result;
    }

}
