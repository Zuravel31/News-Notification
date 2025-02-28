!/bin/bash

# Переменные для подключения к базе данных
DB_NAME="new"
DB_USER="your_username"  # Замените на нужный логин
DB_PASSWORD="your_password"  # Замените на нужный пароль
DB_HOST="localhost"
DB_PORT="5432"

# Создание базы данных
echo "Создание базы данных..."
createdb -h $DB_HOST -p $DB_PORT -U $DB_USER $DB_NAME

# Подключение к базе данных и выполнение SQL-команд
echo "Создание таблиц и вставка данных..."
psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME <<EOF

-- Создание таблицы newz в схеме public, если она не существует
CREATE TABLE IF NOT EXISTS public.newz (
    id       BIGSERIAL PRIMARY KEY,
    time     TIMESTAMP NULL,
    keywords TEXT      NOT NULL,
    text    TEXT    NOT NULL UNIQUE,
    is_sent BOOLEAN NOT NULL DEFAULT false
);

-- Создание таблицы users в схеме public, если она не существует
CREATE TABLE IF NOT EXISTS public.users (
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    telegram_chat_id BIGINT NOT NULL UNIQUE
);

-- Вставка начальных данных в таблицу users, если они еще не существуют
INSERT INTO public.users (name, telegram_chat_id)
VALUES
    ('Daneel Zhuravel', 871004053),
    ('Aleksandr Tufanov', 395819127)
ON CONFLICT (telegram_chat_id) DO NOTHING;
EOF

echo "База данных успешно инициализирована."
