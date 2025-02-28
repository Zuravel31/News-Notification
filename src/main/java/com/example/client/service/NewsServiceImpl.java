package com.example.client.service;

import com.example.client.client.NewsClient;
import com.example.client.dto.NewsDTO;
import com.example.client.entity.News;
import com.example.client.mapper.NewsMapper;
import com.example.client.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper mapper;

    private final NewsRepository newsRepository;

    private final NewsClient client;

    public void fetchAndSaveAllNews() {
        List<NewsDTO> newsDTOs = client.getAllNews();
        if (newsDTOs != null && !newsDTOs.isEmpty()) {
            List<News> newsList = newsDTOs.stream()
                    .map(mapper::toEntity)
                    .toList();

            for (News news : newsList) {
                // Проверка наличия дубликата перед сохранением
                Optional<News> existingNews = newsRepository.findByText(news.getText());
                if (existingNews.isPresent()) {
                    // Логика для обработки дубликата
//                    log.warn("Пропущена дублирующаяся новость: {}", news.getText());
                } else {
                    // Установка значения для isSent, если оно не установлено
                    if (news.getIsSent() == null) {
                        news.setIsSent(false); // или true, в зависимости от логики
                    }

                    // Сохранение новости
                    try {
                        newsRepository.save(news);
                        log.info("Новость сохранена: {}", news.getText());
                    } catch (DataIntegrityViolationException e) {
                        // Обработка других возможных ошибок целостности данных
                        log.error("Ошибка при сохранении новости: {}", news.getText(), e);
                    }
                }
            }

            log.info("Сохранено {} новостей в базе данных.", newsList.size());
        } else {
            log.warn("Не получено новостей от клиента.");
        }
    }


    @Scheduled(fixedRate = 300_000)  // Запускать каждые 5 минут
    public void checkNews() {
        log.info("Начало метода checkNews");

        try {
            log.info("Запрос новостей у клиента");
            fetchAndSaveAllNews();
            log.info("Завершение метода checkNews");
        } catch (Exception e) {
            log.error("Ошибка при проверке новостей", e);
        }
    }
}


