package com.example.client.client;

import com.example.client.dto.NewsDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(name = "messageClient", url = "https://tender-heads-bathe.loca.lt/news")
public interface NewsClient {

    @CircuitBreaker(name = "newsClient", fallbackMethod = "fallbackGetAllNews")
    @GetMapping("/all")
    List<NewsDTO> getAllNews();

}
