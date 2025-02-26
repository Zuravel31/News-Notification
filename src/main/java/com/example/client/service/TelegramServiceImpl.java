package com.example.client.service;

import com.example.client.dto.NewsDTO;
import com.example.client.dto.UserDTO;
import com.example.client.entity.News;
import com.example.client.entity.User;
import com.example.client.job.TelegramBot;
import com.example.client.mapper.NewsMapper;
import com.example.client.mapper.UserMapper;
import com.example.client.repository.NewsRepository;
import com.example.client.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TelegramServiceImpl implements TelegramService {

    private final NewsMapper newsMapper;

    private final UserMapper userMapper;

    private final TelegramBot bot;

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    public void sendMessagesToTelegram() {
        List<News> messages = newsRepository.findAll();
        log.info("Found {} news messages in the database.", messages.size());

        List<NewsDTO> messageDTOS = messages.stream()
                .map(newsMapper::toDto)
                .collect(Collectors.toList());

        List<User> users = userRepository.findAll();
        log.info("Found {} users in the database.", users.size());

        List<UserDTO> userDTOS = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());

        for (UserDTO userDTO : userDTOS) {
            Long chatId = userDTO.getTelegramChatId();
            for (NewsDTO messageDTO : messageDTOS) {
                String message = formatMessage(messageDTO);
                bot.sendMessage(chatId, message);
            }
        }
    }

    public String formatMessage(NewsDTO messageDTO) {
        return String.format(
                " **Time:** %s\n" +
                        " **Keywords:** %s\n" +
                        " **Text:** %s",
                messageDTO.getTime(),
                messageDTO.getKeywords(),
                messageDTO.getText()
        );
    }
}
