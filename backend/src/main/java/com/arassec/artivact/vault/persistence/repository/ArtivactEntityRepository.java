package com.arassec.artivact.vault.persistence.repository;

import com.arassec.artivact.vault.persistence.model.ArtivactEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ArtivactEntityRepository extends PagingAndSortingRepository<ArtivactEntity, String> {

    void deleteAllByScannedBefore(LocalDateTime scanTime);

}
