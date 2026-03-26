package ru.itis.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Post {
    Long id;
    Long sourceId;
    String title;
    String link;
    String description;
    LocalDateTime publishedAt;
    String guid; // идентификатор в rss
}
