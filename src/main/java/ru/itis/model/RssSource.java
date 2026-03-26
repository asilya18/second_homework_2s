package ru.itis.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RssSource {
    Long id;
    Long userId;
    String title;
    String link;
    String description;
}
