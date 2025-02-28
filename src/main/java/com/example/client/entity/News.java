package com.example.client.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * сущность новости, которая хранится в базе данных.
 * Содержит информацию о времени создания, ключевых словах, тексте новости и статусе отправки.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "newz")
@Entity
public class News {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    @Column(columnDefinition = "TEXT")
    private String keywords;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(name = "is_sent", nullable = false)
    private Boolean isSent;
}