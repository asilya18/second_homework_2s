package ru.itis.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Session {
    private String sessionId;
    private Long userId;
    private LocalDateTime expireAt;
}
