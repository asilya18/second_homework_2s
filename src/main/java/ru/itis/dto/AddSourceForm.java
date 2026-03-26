package ru.itis.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.itis.validator.rssUrl.ValidRssUrl;


@Data
public class AddSourceForm {

    @NotBlank(message = "title не может быть пустым")
    private String title;

    @ValidRssUrl
    private String link;

    private String description;
}
