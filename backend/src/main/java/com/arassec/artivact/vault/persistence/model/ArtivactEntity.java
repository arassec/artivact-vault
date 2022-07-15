package com.arassec.artivact.vault.persistence.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "artivact")
public class ArtivactEntity {

    @Id
    private String id;

    @Version
    private Integer version;

    private LocalDateTime scanned;

    private String name;

    private String mediaContentJson;

}
