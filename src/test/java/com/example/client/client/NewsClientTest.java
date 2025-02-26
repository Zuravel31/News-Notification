package com.example.client.client;

import com.example.client.dto.NewsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class NewsClientTest {

   @Mock
    private NewsClient client;

    @Test
    void testGetAllNews() {

        LocalDateTime now = LocalDateTime.now();
        // Создаем моковые данные
        NewsDTO news1 = new NewsDTO(now, "Новость 4","Описание 5");
        NewsDTO news2 = new NewsDTO(now, "Новость 6", "Описание 7");

        List<NewsDTO> mockNewsList = Arrays.asList(news1, news2);

        // Настраиваем mock, чтобы он возвращал наши данные
        given(client.getAllNews()).willReturn(mockNewsList);

        // Вызываем метод и проверяем результат
        List<NewsDTO> result = client.getAllNews();
        assertThat(result).hasSize(2);
        assertThat(result).contains(news1, news2);
    }
}