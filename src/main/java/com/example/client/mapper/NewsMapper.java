package com.example.client.mapper;


import com.example.client.dto.NewsDTO;
import com.example.client.entity.News;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsDTO toDto(News news);

    News toEntity(NewsDTO newsDTO);

}

