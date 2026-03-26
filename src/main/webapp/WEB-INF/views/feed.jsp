<%--
  Created by IntelliJ IDEA.
  User: asilyashakirova
  Date: 25.03.2026
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newspaper.css">
    <title>лента постов</title>
</head>
<body>
<div class="container">
<h1>привет, ${user.username}!</h1>
<p>
    <a href="/sources">мои источники</a>
</p>
<h2>лента</h2>

<c:if test="${empty posts}">
    <p>пока нет постов. добавьте источники.</p>
</c:if>

<c:forEach var="post" items="${posts}">
    <div style="border:1px solid #ccc; padding:10px; margin:10px 0;">
        <h3>
            <a href="/posts/${post.id}">
                    ${post.title}
            </a>
        </h3>
        <p>
            <b>источник:</b>
            <c:out value="${sourceNames[post.sourceId]}"/>
        </p>
        <c:if test="${not empty post.publishedAt}">
            <p><b>Дата:</b> ${post.publishedAt}</p>
        </c:if>
    </div>
</c:forEach>

<div style="margin-top:20px;">
    <c:if test="${page > 0}">
        <a href="/feed?page=${page - 1}&size=${size}">назад</a>
    </c:if>
    <span style="margin:0 10px;">
        страница ${page + 1}
    </span>
    <c:if test="${(page + 1) * size < total}">
        <a href="/feed?page=${page + 1}&size=${size}">вперёд</a>
    </c:if>
</div>
</div>
</body>
</html>
