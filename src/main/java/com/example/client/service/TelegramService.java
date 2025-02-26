package com.example.client.service;

import com.example.client.dto.NewsDTO;

public interface TelegramService {

        public void sendMessagesToTelegram();

        public String formatMessage(NewsDTO messageDTO);
}
