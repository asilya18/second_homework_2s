package ru.itis.service;

import org.springframework.stereotype.Service;
import ru.itis.model.RssSource;
import ru.itis.repository.RssSourceRepository;
import java.util.List;

@Service
public class RssSourceService {

    private final RssSourceRepository rssSourceRepository;

    public RssSourceService(RssSourceRepository rssSourceRepository) {
        this.rssSourceRepository = rssSourceRepository;
    }

    public Long addSource(Long userId, String title, String link, String description) {
        String normalizedNew = normalize(link);
        List<RssSource> sources = rssSourceRepository.findByUserId(userId);
        for (RssSource s : sources) {
            String normalizedExisting = normalize(s.getLink());
            if (normalizedExisting.equals(normalizedNew)) {
                throw new RuntimeException("source already exists");
            }
        }
        RssSource source = RssSource.builder()
                .userId(userId)
                .title(title)
                .link(link)
                .description(description)
                .build();
        return rssSourceRepository.save(source);
    }

    private String normalize(String url) {
        if (url == null) return null;
        url = url.trim().toLowerCase();
        // убираем / в конце
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    public List<RssSource> getSourcesByUser(Long userId) {
        return rssSourceRepository.findByUserId(userId);
    }

    public RssSource getById(Long id) {
        return rssSourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("source not found"));
    }

    public void delete(Long id) {
        rssSourceRepository.deleteById(id);
    }

}
