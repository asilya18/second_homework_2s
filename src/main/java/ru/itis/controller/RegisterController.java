package ru.itis.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.RegisterForm;
import ru.itis.service.UserService;

@Controller
public class RegisterController {
    private final UserService userService;
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(
            // создаем обьект формы и заполняем введенными значениями
            @Valid @ModelAttribute("form") RegisterForm form,
            // содержит ли ошибки валидации
            BindingResult bindingResult,
            // модель для передачи ошибок в jsp
            Model model,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            System.out.println("errors: " + bindingResult.getAllErrors());
            return "register";
        }
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("globalError", "пароли не совпадают");
            return "register";
        }
        try {
            String sessionId = userService.register(
                    form.getUsername(),
                    form.getEmail(),
                    form.getPassword()
            );
            Cookie cookie = new Cookie("SESSION_ID", sessionId);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);
            return "redirect:/feed";
        } catch (RuntimeException e) {
            model.addAttribute("globalError", e.getMessage());
            return "register";
        }
    }
}
