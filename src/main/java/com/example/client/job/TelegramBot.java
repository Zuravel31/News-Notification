package com.example.client.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    String botToken;

    @Value("${bot.username}")
    String botUsername;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        // Логирование перед выполнением запроса
        log.info("Отправка сообщения в чатId: " + chatId + " с текстом: " + text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Не удалось отправить сообщение в чатId: " + chatId, e);
        }

        // Логирование после выполнения запроса
        log.info("Сообщение успешно отправлено в чатId: " + chatId);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
// Этот метод можно оставить пустым, так как мы не обрабатываем входящие сообщения
    }
}
