package ru.itis.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DescriptionPreviewFormatter implements Formatter<String> {
    @Override
    public String print(String description, Locale locale) {
        if (description == null) return "";
        if (description.length() > 200) {
            return description.substring(0, 197) + "...";
        }
        return description;
    }
    @Override
    public String parse(String text, Locale locale) {
        return text; // метод пустой тк текст вводится не вручную
    }
}
