<%--
  Created by IntelliJ IDEA.
  User: asilyashakirova
  Date: 25.03.2026
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newspaper.css">
    <title>мои источники</title>
</head>
<body>
<div class="container">
<h1>профиль: ${user.username}</h1>
<p>
    <a href="/feed">лента</a> |
    <a href="/logout">выйти</a>
</p>

<h2>мои источники</h2>

<c:if test="${empty sources}">
    <p>у вас пока нет источников. добавьте новый ниже.</p>
</c:if>

<c:forEach var="src" items="${sources}">
    <div style="border:1px solid #ccc; padding:10px; margin:10px 0;">
        <h3>${src.title}</h3>
        <p><b>ссылка:</b> ${src.link}</p>
        <c:if test="${not empty src.description}">
            <p><b>описание:</b> ${src.description}</p>
        </c:if>
    </div>
</c:forEach>

<h3>добавить новый источник</h3>
<form action="/sources/add" method="post">
    <div>
        <label>название:</label><br>
        <input type="text" name="title" value="${form.title}"/>
        <c:if test="${not empty formErrors['title']}">
            <span style="color:red">${formErrors['title']}</span>
        </c:if>
    </div>
    <br>
    <div>
        <label>ссылка (URL):</label><br>
        <input type="text" name="link" value="${form.link}"/>
        <c:if test="${not empty formErrors['link']}">
            <span style="color:red">${formErrors['link']}</span>
        </c:if>
    </div>
    <br>
    <div>
        <label>описание (необязательно):</label><br>
        <textarea name="description" rows="3" cols="40">${form.description}</textarea>
        <c:if test="${not empty formErrors['description']}">
            <span style="color:red">${formErrors['description']}</span>
        </c:if>
    </div>
    <br>
    <div>
        <input type="submit" value="добавить источник"/>
    </div>
</form>
</div>
</body>
</html>
