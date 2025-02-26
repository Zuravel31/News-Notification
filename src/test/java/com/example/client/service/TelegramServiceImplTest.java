package com.example.client.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.client.dto.NewsDTO;
import com.example.client.dto.UserDTO;
import com.example.client.entity.News;
import com.example.client.entity.User;
import com.example.client.job.TelegramBot;
import com.example.client.mapper.NewsMapper;
import com.example.client.mapper.UserMapper;
import com.example.client.repository.NewsRepository;
import com.example.client.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TelegramServiceImplTest {

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TelegramBot bot;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TelegramServiceImpl telegramService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessagesToTelegram() {
        // Arrange
        News news1 = new News();
        News news2 = new News();
        List<News> newsList = Arrays.asList(news1, news2);

        NewsDTO newsDTO1 = new NewsDTO();
        NewsDTO newsDTO2 = new NewsDTO();

        when(newsRepository.findAll()).thenReturn(newsList);
        when(newsMapper.toDto(news1)).thenReturn(newsDTO1);
        when(newsMapper.toDto(news2)).thenReturn(newsDTO2);

        User user1 = new User();
        List<User> userList = Arrays.asList(user1);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setTelegramChatId(123456L);

        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.toDto(user1)).thenReturn(userDTO1);

        // Act
        telegramService.sendMessagesToTelegram();

        // Assert
        verify(bot, times(2)).sendMessage(anyLong(), anyString());
    }

    @Test
    public void testFormatMessage() {
        // Arrange
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTime(LocalDateTime.parse("2023-10-01T12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        newsDTO.setKeywords("keyword1, keyword2");
        newsDTO.setText("This is a test message.");

        // Act
        String formattedMessage = telegramService.formatMessage(newsDTO);

        // Assert
        String expectedMessage = " **Time:** 2023-10-01T12:00\n" +
                " **Keywords:** keyword1, keyword2\n" +
                " **Text:** This is a test message.";
        assertEquals(expectedMessage, formattedMessage);
    }
}
