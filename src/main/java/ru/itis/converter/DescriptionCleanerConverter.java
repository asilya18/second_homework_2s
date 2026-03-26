package ru.itis.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DescriptionCleanerConverter implements Converter<String, String> {
    @Override
    public String convert(String source) {
        if (source == null){
            return null;
        }
        // убираем HTML-теги
        String cleaned = source.replaceAll("<[^>]*>", "");
        // убираем лишние пробелы
        cleaned = cleaned.trim().replaceAll("\\s+", " ");
        return cleaned;
    }
}
