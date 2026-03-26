package ru.itis.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error"; // для всех непредвиденных ошибок
    }
    @ExceptionHandler(RssLoadingException.class)
    public String handleRssLoading(RssLoadingException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
