package com.example.client.job;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@ExtendWith(MockitoExtension.class)
public class TelegramBotTest {

    @Spy
    @InjectMocks
    private TelegramBot telegramBot;

    @BeforeEach
    public void setUp() {
        telegramBot = spy(new TelegramBot());
    }

    @Test
    public void testSendMessage() throws TelegramApiException {
        // Arrange
        Long chatId = 123456L;
        String text = "Test message";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);

        // Act
        telegramBot.sendMessage(chatId, text);

        // Assert
        verify(telegramBot).execute(any(SendMessage.class));
    }

    @Test
    public void testSendMessage_Exception() throws TelegramApiException {
        // Arrange
        Long chatId = 123456L;
        String text = "Test message";
        doThrow(new TelegramApiException("Test exception")).when(telegramBot).execute(any(SendMessage.class));

        // Act & Assert
        try {
            telegramBot.sendMessage(chatId, text);
        } catch (Exception e) {
            // Expected exception
        }

        verify(telegramBot).execute(any(SendMessage.class));
    }
}
