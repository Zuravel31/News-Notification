package com.example.client.service;

import com.example.client.client.NewsClient;
import com.example.client.dto.NewsDTO;
import com.example.client.entity.News;
import com.example.client.mapper.NewsMapper;
import com.example.client.repository.NewsRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper mapper;

    private final NewsRepository newsRepository;

    private final NewsClient client;

//    private final Set<String> uniqueEntries = new HashSet<>();

    public void fetchAndSaveAllNews() {
        List<NewsDTO> newsDTOs = client.getAllNews();
        if (newsDTOs != null && !newsDTOs.isEmpty()) {
            List<News> newsList = newsDTOs.stream()
                    .map(mapper::toEntity)
                    .toList();

            for (News news : newsList) {
                try {
                    newsRepository.save(news);
                    log.info("Новость сохранена: {}", news.getText());
                } catch (DataIntegrityViolationException e) {
                    // Пропустить дублирующуюся запись и продолжить
                    log.warn("Пропущена дублирующаяся новость: {}", news.getText());
                }
            }

            log.info("Сохранено {} новостей в базе данных.", newsList.size());
        } else {
            log.warn("Не получено новостей от клиента.");
        }
    }


//    @Override
//    @Retry(name = "newsRetry")
//    public void fetchAndSaveAllNews() {
//        List<NewsDTO> newsDTOs = client.getAllNews();
//        if (newsDTOs != null && !newsDTOs.isEmpty()) {
//            for (NewsDTO newsDTO : newsDTOs) {
////                saveNews(newsDTO.getTime().toString(), newsDTO.getKeywords(), newsDTO.getText());
//            }
//            log.info("Сохранено {} новостей в базе данных.", newsDTOs.size());
//        } else {
//            log.warn("Не получено новостей от клиента.");
//        }
//    }

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

//    public void saveNews(String time, String foundKeywords, String text) {
//        String logMessage = time + "ч " + foundKeywords + " " + text;
//        if (uniqueEntries.add(logMessage)) {
//            log.info(logMessage);
//            if (!newsRepository.existsByText(text)) {
//                NewsDTO newsDTO = new NewsDTO();
//                newsDTO.setTime(LocalDateTime.now());
//                newsDTO.setKeywords(foundKeywords);
//                newsDTO.setText(text);
//                News newz = mapper.toEntity(newsDTO);
//
//            }
//        }
    }


