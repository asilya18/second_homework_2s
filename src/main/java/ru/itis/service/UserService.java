package ru.itis.service;

import org.springframework.stereotype.Service;
import ru.itis.model.Session;
import ru.itis.model.User;
import ru.itis.repository.SessionRepository;
import ru.itis.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public UserService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public String register(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("username already exists");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("email already exists");
        }
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        User user = User.builder()
                .username(username)
                .email(email)
                .passwordHash(hash)
                .salt(salt)
                .build();
        Long userId = userRepository.save(user);
        System.out.println("Creating session for user " + userId);
        return createSession(userId);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("такого аккаунта не существует"));
        String expectedHash = hashPassword(password, user.getSalt());
        if (!expectedHash.equals(user.getPasswordHash())) {
            throw new RuntimeException("неверный пароль");
        }
        return createSession(user.getId());
    }

    public User getUserBySessionId(String sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("session not found"));
        if (session.getExpireAt().isBefore(LocalDateTime.now())) {
            sessionRepository.deleteById(sessionId);
            throw new RuntimeException("session expired");
        }
        return userRepository.findById(session.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public void logout(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    private String createSession(Long userId) {
        String sessionId = UUID.randomUUID().toString();
        LocalDateTime expireAt = LocalDateTime.now().plusDays(1);
        Session session = Session.builder()
                .sessionId(sessionId)
                .userId(userId)
                .expireAt(expireAt)
                .build();
        sessionRepository.save(session);
        return sessionId;
    }

    private String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashed = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
