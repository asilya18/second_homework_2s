package ru.itis.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.model.Post;
import ru.itis.model.RssSource;
import ru.itis.model.User;
import ru.itis.service.PostService;
import ru.itis.service.RssSourceService;
import ru.itis.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class FeedController {

    private final PostService postService;
    private final RssSourceService rssSourceService;
    private final UserService userService;

    public FeedController(PostService postService, RssSourceService rssSourceService, UserService userService) {
        this.postService = postService;
        this.rssSourceService = rssSourceService;
        this.userService = userService;
    }

    @GetMapping("/feed")
    public String feed(HttpServletRequest request,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    try {
                        user = userService.getUserBySessionId(cookie.getValue());
                    } catch (RuntimeException e) {
                        return "redirect:/login"; // если сессия не найдена или истекла
                    }
                    break;
                }
            }
        }
        if (user == null) {
            return "redirect:/login";
        }
        // посты обновляются при каждом обновлении ленты
        List<Post> allPosts = postService.getFeedForUser(user.getId());
        int from = page * size;
        int to = Math.min(from + size, allPosts.size());
        List<Post> pagePosts = from < allPosts.size()
                ? allPosts.subList(from, to)
                : List.of();

        List<RssSource> sources = rssSourceService.getSourcesByUser(user.getId());
        Map<Long, String> sourceNames = sources.stream()
                .collect(Collectors.toMap(RssSource::getId, RssSource::getTitle));

        model.addAttribute("user", user);
        model.addAttribute("posts", pagePosts);
        model.addAttribute("sourceNames", sourceNames);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", allPosts.size());
        return "feed";
    }

}
