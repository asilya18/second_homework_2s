package ru.itis.validator.rssUrl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.net.URL;

public class RssUrlValidator implements ConstraintValidator<ValidRssUrl, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        try {
            URL url = new URL(value);
            // проверка что URL открывается и содержит XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url.openStream());
            // проверка что это RSS/Atom
            boolean isRss = doc.getElementsByTagName("item").getLength() > 0;
            boolean isAtom = doc.getElementsByTagName("entry").getLength() > 0;

            return isRss || isAtom;

        } catch (Exception e) {
            return false;
        }
    }
}
