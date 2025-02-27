package com.example.client.repository;

import com.example.client.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByIsSentFalse();

    @Modifying
    @Query("UPDATE News n SET n.isSent = true WHERE n.id = :id")
    void updateSentStatus(@Param("id") Long id);

    Optional<News> findByText(String text);
}
