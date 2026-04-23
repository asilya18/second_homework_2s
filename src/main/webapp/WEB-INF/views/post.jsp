<%--
  Created by IntelliJ IDEA.
  User: asilyashakirova
  Date: 25.03.2026
  Time: 22:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="http://itis.ru/tags" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newspaper.css">
    <title>${post.title}</title>
</head>
<body>
<div class="container">

    <p><a href="/feed">← назад в ленту</a></p>

    <h1>${post.title}</h1>

    <c:if test="${not empty post.publishedAt}">
        <p><b>Дата публикации:</b> ${post.publishedAt}</p>
    </c:if>

    <c:if test="${not empty post.description}">
        <p><my:preview text="${post.description}" /></p>
    </c:if>

    <c:if test="${not empty post.link}">
        <p>
            <a href="${post.link}" target="_blank">перейти к оригиналу</a>
        </p>
    </c:if>

</div>
</body>
</html>
