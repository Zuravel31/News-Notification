CREATE TABLE if not exists newz
(
    id       BIGSERIAL PRIMARY KEY,
    time     TIMESTAMP NULL,
    keywords TEXT      NOT NULL,
    text    TEXT    NOT NULL UNIQUE,
    is_sent BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE users
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    telegram_chat_id BIGINT NOT NULL UNIQUE
);
-- UPDATE newz
-- SET is_sent = false WHERE is_sent IS NULL;
