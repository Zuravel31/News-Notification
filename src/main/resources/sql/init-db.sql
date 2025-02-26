CREATE TABLE IF NOT EXISTS newz
(
    id       BIGSERIAL PRIMARY KEY,
    time     TIMESTAMP NULL,
    keywords TEXT      NOT NULL,
    text     TEXT      NOT NULL UNIQUE
);

CREATE TABLE users
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    telegram_chat_id BIGINT NOT NULL UNIQUE
);