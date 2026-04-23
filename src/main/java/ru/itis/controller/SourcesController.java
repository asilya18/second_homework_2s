package ru.itis.controller;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.AddSourceForm;
import ru.itis.model.RssSource;
import ru.itis.model.User;
import ru.itis.service.RssSourceService;
import ru.itis.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SourcesController {
    private final RssSourceService rssSourceService;
    private final UserService userService;

    public SourcesController(RssSourceService rssSourceService, UserService userService) {
        this.rssSourceService = rssSourceService;
        this.userService = userService;
    }

    @GetMapping("/sources")
    public String sources(HttpServletRequest request, Model model) {
        User user = getUserFromCookies(request);
        if (user == null) {
            return "redirect:/login";
        }
        List<RssSource> sources = rssSourceService.getSourcesByUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("sources", sources);
        return "sources";
    }

    @PostMapping("/sources/add")
    public String addSource(HttpServletRequest request,
                            @Valid @ModelAttribute("form") AddSourceForm form,
                            BindingResult result,
                            Model model) {
        User user = getUserFromCookies(request);
        if (user == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            List<RssSource> sources = rssSourceService.getSourcesByUser(user.getId());
            model.addAttribute("user", user);
            model.addAttribute("sources", sources);
            model.addAttribute("form", form);
            model.addAttribute("formErrors", result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            fieldError -> fieldError.getField(),
                            fieldError -> fieldError.getDefaultMessage()
                    )));
            return "sources"; // та же страница, но с сообщениями об ошибках
        }
        rssSourceService.addSource(user.getId(), form.getTitle(), form.getLink(), form.getDescription());
        return "redirect:/sources";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    userService.logout(cookie.getValue());
                    Cookie expired = new Cookie("SESSION_ID", "");
                    expired.setPath("/");
                    expired.setMaxAge(0);
                    response.addCookie(expired);
                    break;
                }
            }
        }
        return "redirect:/login";
    }

    private User getUserFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    try {
                        return userService.getUserBySessionId(cookie.getValue());
                    } catch (RuntimeException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
