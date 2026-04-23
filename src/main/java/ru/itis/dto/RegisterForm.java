package ru.itis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.itis.validator.email.ValidEmailDomain;

@Data
public class RegisterForm {
    @NotBlank(message = "username не может быть пустым")
    private String username;
    @NotBlank(message = "email не может быть пустым")
    @Email(message = "некорректный формат email")
    // проверяет только что‑то@что‑то.домен
    @ValidEmailDomain(allowed = {"gmail.com", "yandex.ru", "mail.ru"}, message = "разрешены только домены gmail.com/yandex.ru/mail.ru")
    private String email;
    @NotBlank(message = "пароль не может быть пустым")
    @Size(min = 3, max = 50, message = "пароль должен быть от 3 до 50 символов")
    private String password;
    @NotBlank(message = "подтверждение пароля не может быть пустым")
    @Size(min = 3, max = 50, message = "подтверждение пароля должно быть от 3 до 50 символов")
    private String confirmPassword;
}
