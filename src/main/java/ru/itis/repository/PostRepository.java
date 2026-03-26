package ru.itis.repository;

import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.model.Post;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Post> postRowMapper = (rs, rowNum) -> Post.builder()
            .id(rs.getLong("id"))
            .sourceId(rs.getLong("source_id"))
            .title(rs.getString("title"))
            .link(rs.getString("link"))
            .description(rs.getString("description"))
            .publishedAt(rs.getTimestamp("published_at") != null
                    ? rs.getTimestamp("published_at").toLocalDateTime()
                    : null)
            .guid(rs.getString("guid"))
            .build();

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> findAll() {
        String sql = "SELECT id, source_id, title, link, description, published_at, guid FROM posts ORDER BY published_at DESC";
        return jdbcTemplate.query(sql, postRowMapper);
    }

    public List<Post> findBySourceId(Long sourceId) {
        String sql = "SELECT id, source_id, title, link, description, published_at, guid FROM posts WHERE source_id = ? ORDER BY published_at DESC";
        return  jdbcTemplate.query(sql, postRowMapper, sourceId);
    }

    public Optional<Post> findByGuid(String guid) {
        String sql = "SELECT id, source_id, title, link, description, published_at, guid FROM posts WHERE guid = ?";
        try {
            Post post = jdbcTemplate.queryForObject(sql, postRowMapper, guid);
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existsByGuid(String guid) {
        String sql = "SELECT count(*) FROM posts WHERE guid = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, guid);
        return count != null && count > 0;
    }

    public Long save(Post post) {
        String sql = "INSERT INTO posts (source_id, title, link, description, published_at, guid) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setLong(1, post.getSourceId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getLink());
            ps.setString(4, post.getDescription());
            ps.setTimestamp(5, post.getPublishedAt() != null ? Timestamp.valueOf(post.getPublishedAt()) : null);
            ps.setString(6, post.getGuid());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }


    public void deleteBySourceId(Long sourceId) {
        String sql = "DELETE FROM posts WHERE source_id = ?";
        jdbcTemplate.update(sql, sourceId);
    }

    public void deleteById(Long Id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        jdbcTemplate.update(sql, Id);
    }

    public Optional<Post> findById(Long id) {
        String sql = "SELECT id, source_id, title, link, description, published_at, guid FROM posts WHERE id = ?";
        try {
            Post post = jdbcTemplate.queryForObject(sql, postRowMapper, id);
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
