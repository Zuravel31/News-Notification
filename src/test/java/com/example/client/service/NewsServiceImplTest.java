package com.example.client.service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.client.client.NewsClient;
import com.example.client.dto.NewsDTO;
import com.example.client.entity.News;
import com.example.client.mapper.NewsMapper;
import com.example.client.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceImplTest {

    @Mock
    private NewsClient client;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper mapper;

    @Spy
    @InjectMocks
    private NewsServiceImpl newsService;
    @Test
    void testFetchAndSaveAllNews() {
        LocalDateTime now = LocalDateTime.now();
        NewsDTO news1 = new NewsDTO(now, "Новость 1", "Описание 1");
        NewsDTO news2 = new NewsDTO(now, "Новость 2", "Описание 2");
        List<NewsDTO> mockNewsList = Arrays.asList(news1, news2);

        given(client.getAllNews()).willReturn(mockNewsList);
        given(mapper.toEntity(news1)).willReturn(new News());
        given(mapper.toEntity(news2)).willReturn(new News());

        newsService.fetchAndSaveAllNews();

        verify(newsRepository).saveAll(anyList());
    }

    @Test
    void testCheckNews_Success() {
        doNothing().when(newsService).fetchAndSaveAllNews();

        newsService.checkNews();

        verify(newsService, times(1)).fetchAndSaveAllNews();
    }


    @Test
    void testCheckNews_Exception() {
        doThrow(new RuntimeException("Ошибка при проверке новостей")).when(newsService).fetchAndSaveAllNews();

        newsService.checkNews();

        verify(newsService, times(1)).fetchAndSaveAllNews();
    }
}
