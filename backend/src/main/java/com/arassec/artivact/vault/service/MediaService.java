package com.arassec.artivact.vault.service;

import com.arassec.artivact.vault.service.model.ImageSize;
import com.arassec.artivact.vault.util.ArtivactVaultException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static com.arassec.artivact.vault.service.MediaService.IMAGES;

@Service
@CacheConfig(cacheNames = {IMAGES})
public class MediaService {

    public static final String IMAGES = "images";

    public static final String MODELS = "models";

    @Value("${artivacts.dir}")
    private String artivactsDir;

    @Cacheable(IMAGES)
    public byte[] getArtivactImage(String artivactId, String fileName, ImageSize targetSize) {
        try {
            var bufferedImage = ImageIO.read(Path.of(artivactsDir)
                    .resolve(artivactId.substring(0, 3))
                    .resolve(artivactId.substring(3, 6))
                    .resolve(artivactId)
                    .resolve(IMAGES)
                    .resolve(fileName)
                    .toFile());

            if (!ImageSize.ORIGINAL.equals(targetSize)) {
                bufferedImage = Scalr.resize(bufferedImage, targetSize.getWidth());
            }

            String[] fileNameParts = fileName.split("\\.");
            String fileEnding = fileNameParts[fileNameParts.length - 1];

            try (var byteArrayOutputStream = new ByteArrayOutputStream()) {
                ImageIO.write(bufferedImage, fileEnding, byteArrayOutputStream);

                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            throw new ArtivactVaultException("Could not read artivact image file!", e);
        }
    }

    public FileSystemResource getArtivactModel(String artivactId, String fileName) {
        return new FileSystemResource(Path.of(artivactsDir)
                .resolve(artivactId.substring(0, 3))
                .resolve(artivactId.substring(3, 6))
                .resolve(artivactId)
                .resolve(MODELS)
                .resolve(fileName));
    }

}
