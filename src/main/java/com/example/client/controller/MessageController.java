package com.example.client.controller;

import com.example.client.client.NewsClient;
import com.example.client.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class MessageController {

    private final NewsClient newsClient;


    @GetMapping("/all")
    public List<NewsDTO> allNews() {
       return newsClient.getAllNews();
    }
}
