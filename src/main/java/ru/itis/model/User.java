package ru.itis.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    Long id;
    String username;
    String passwordHash;
    String email;
    String salt;
}
