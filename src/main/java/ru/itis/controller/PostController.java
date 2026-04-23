package ru.itis.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.model.Post;
import ru.itis.model.User;
import ru.itis.service.PostService;
import ru.itis.service.UserService;

@Controller
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/posts/{id}")
    // @PathVariable берет id из пути в url
    public String getPostPage(@PathVariable Long id,
                              HttpServletRequest request,
                              Model model) {
        User user = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    try {
                        user = userService.getUserBySessionId(cookie.getValue());
                    } catch (RuntimeException e) {
                        return "redirect:/login";
                    }
                    break;
                }
            }
        }
        if (user == null) {
            return "redirect:/login";
        }
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);

        return "post";
    }
}
