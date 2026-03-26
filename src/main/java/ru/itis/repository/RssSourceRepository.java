package ru.itis.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.model.RssSource;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class RssSourceRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<RssSource> rssSourceRowMapper = (rs, rowNum) -> RssSource.builder()
            .id(rs.getLong("id"))
            .userId(rs.getLong("user_id"))
            .title(rs.getString("title"))
            .link(rs.getString("link"))
            .description(rs.getString("description"))
            .build();

    public RssSourceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RssSource> findAll() {
        String sql = "SELECT id, user_id, title, link, description FROM rss_sources ORDER BY id";
        return jdbcTemplate.query(sql, rssSourceRowMapper);
    }

    public List<RssSource> findByUserId(Long userId) {
        String sql = "SELECT id, user_id, title, link, description FROM rss_sources WHERE user_id = ?";
        return jdbcTemplate.query(sql, rssSourceRowMapper, userId);
    }

    public Optional<RssSource> findById(Long id) {
        String sql = "SELECT id, user_id, title, link, description FROM rss_sources WHERE id = ?";
        try {
            RssSource source = jdbcTemplate.queryForObject(sql, rssSourceRowMapper, id);
            return Optional.ofNullable(source);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long save(RssSource source) {
        String sql = "INSERT INTO rss_sources (user_id, title, link, description) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setLong(1, source.getUserId());
            ps.setString(2, source.getTitle());
            ps.setString(3, source.getLink());
            ps.setString(4, source.getDescription());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }


    public void deleteById(Long id) {
        String sql = "DELETE FROM rss_sources WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
