package ru.itis.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.model.Session;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class SessionRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Session> sessionRowMapper = (rs, rowNum) ->
            Session.builder()
                    .sessionId(rs.getString("session_id"))
                    .userId(rs.getLong("user_id"))
                    .expireAt(rs.getTimestamp("expire_at").toLocalDateTime())
                    .build();

    public SessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Session session) {
        String sql = "INSERT INTO sessions (session_id, user_id, expire_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                session.getSessionId(),
                session.getUserId(),
                Timestamp.valueOf(session.getExpireAt())
        );
        System.out.println("Saving session " + session.getSessionId());

    }

    public Optional<Session> findById(String sessionId) {
        String sql = "SELECT session_id, user_id, expire_at FROM sessions WHERE session_id = ?";
        try {
            Session session = jdbcTemplate.queryForObject(sql, sessionRowMapper, sessionId);
            return Optional.ofNullable(session);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(String sessionId) {
        String sql = "DELETE FROM sessions WHERE session_id = ?";
        jdbcTemplate.update(sql, sessionId);
    }
}
