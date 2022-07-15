CREATE TABLE artivact
(
    id                 VARCHAR(128) PRIMARY KEY,
    version            INTEGER,
    scanned            TIMESTAMP,
    name               VARCHAR(256) NOT NULL,
    media_content_json TEXT
);
