package ru.itis.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ParsedPost {
    private String title;
    private String link;
    private String description;
    private String guid;
    private LocalDateTime publishedAt;
}
