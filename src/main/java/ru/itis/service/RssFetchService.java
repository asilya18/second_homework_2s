package ru.itis.service;

import org.springframework.stereotype.Service;
import ru.itis.dto.ParsedPost;
import ru.itis.exception.RssLoadingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssFetchService {
    public List<ParsedPost> fetch(String urlString){
        List<ParsedPost> result = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            // фабрика которая создает парсеры
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // сам парсер
            DocumentBuilder builder = factory.newDocumentBuilder();
            // скачиваем XML, превращаем в dom-дерево
            org.w3c.dom.Document doc = builder.parse(url.openStream());
            org.w3c.dom.NodeList items = doc.getElementsByTagName("item");
            for (int i=0; i<items.getLength(); i++) {
                org.w3c.dom.Node item = items.item(i);
                String title = getTagValue(item, "title");
                String link = getTagValue(item, "link");
                String description = getTagValue(item, "description");
                String guid = getTagValue(item, "guid");
                String pubDate = getTagValue(item, "pubDate");

                LocalDateTime publishedAt = null;
                if (pubDate != null) {
                    try {
                        publishedAt = LocalDateTime.ofInstant(
                                java.time.ZonedDateTime.parse(pubDate).toInstant(),
                                ZoneId.systemDefault()
                        );
                    } catch (Exception ignored) {
                    }
                }
                ParsedPost parsed = ParsedPost.builder()
                        .title(title)
                        .link(link)
                        .description(description)
                        .guid(guid)
                        .publishedAt(publishedAt)
                        .build();

                result.add(parsed);
            }
        } catch (Exception e) {
            throw new RssLoadingException("не удалось загрузить RSS по адресу: " + urlString);
        }
        return result;
    }

    // метод для перебора тегов title и др. внутри item
    private String getTagValue(org.w3c.dom.Node item, String tag) {
        // превращаем item в Element, чтобы найти вложенные теги
        org.w3c.dom.NodeList nodeList =
                ((org.w3c.dom.Element) item).getElementsByTagName(tag);
        if (nodeList.getLength() == 0) return null;
        // берем первый найденный тег
        org.w3c.dom.Node node = nodeList.item(0);
        if (node == null || node.getFirstChild() == null) return null;
        return node.getFirstChild().getNodeValue();
    }
}
