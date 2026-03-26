package ru.itis.exception;

// для возможных ошибок URL, XML, соединения при сетевом запросе
public class RssLoadingException extends RuntimeException {
    public RssLoadingException(String message) {
        super(message);
        // super создает runtimeException c этой ошибкой
    }
}