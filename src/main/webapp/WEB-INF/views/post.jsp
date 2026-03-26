<%--
  Created by IntelliJ IDEA.
  User: asilyashakirova
  Date: 25.03.2026
  Time: 22:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <p>${post.description}</p>
</c:if>
<c:if test="${not empty post.link}">
    <p>
        <a href="${post.link}" target="_blank">перейти к оригиналу</a>
    </p>
</c:if>
</div>
</body>
</html>
