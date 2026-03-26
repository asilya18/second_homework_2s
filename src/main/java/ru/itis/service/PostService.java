package ru.itis.service;

import org.springframework.stereotype.Service;
import ru.itis.dto.ParsedPost;
import ru.itis.model.Post;
import ru.itis.model.RssSource;
import ru.itis.repository.PostRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final RssSourceService rssSourceService;
    private final RssFetchService rssFetchService;

    public PostService(PostRepository postRepository,
                       RssSourceService rssSourceService,
                       RssFetchService rssFetchService) {
        this.postRepository = postRepository;
        this.rssSourceService = rssSourceService;
        this.rssFetchService = rssFetchService;
    }

    public void updatePostsForSource(RssSource source) {
        List<ParsedPost> parsedPosts = rssFetchService.fetch(source.getLink());
        for (ParsedPost parsed : parsedPosts) {
            if (parsed.getGuid() == null || parsed.getGuid().isBlank()) {
                continue;
            }
            if (postRepository.existsByGuid(parsed.getGuid())) {
                continue;
            }
            Post post = Post.builder()
                    .sourceId(source.getId())
                    .title(parsed.getTitle())
                    .link(parsed.getLink())
                    .description(parsed.getDescription())
                    .publishedAt(parsed.getPublishedAt())
                    .guid(parsed.getGuid())
                    .build();
            postRepository.save(post);
        }
    }

    public void updateAllForUser(Long userId) {
        List<RssSource> sources = rssSourceService.getSourcesByUser(userId);
        for (RssSource source : sources) {
            updatePostsForSource(source);
        }
    }

    public List<Post> getFeedForUser(Long userId) {
        updateAllForUser(userId);
        List<RssSource> sources = rssSourceService.getSourcesByUser(userId);
        List<Post> allPosts = new ArrayList<>();
        for (RssSource source : sources) {
            allPosts.addAll(postRepository.findBySourceId(source.getId()));
        }
         // сортировка: новые посты сверху
        allPosts.sort(
                Comparator.comparing(Post::getPublishedAt,
                        Comparator.nullsLast(Comparator.reverseOrder()))
        );
        return allPosts;
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post not found"));
    }
}
