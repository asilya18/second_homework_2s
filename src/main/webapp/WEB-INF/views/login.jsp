<%--
  Created by IntelliJ IDEA.
  User: asilyashakirova
  Date: 24.03.2026
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newspaper.css">
    <title>вход</title>
</head>
<body>
<div class="container">
<h1>вход</h1>
<form method="post" action="/login">
    <label>email:</label><br>
    <input type="email" name="email" value="${email}" required><br><br>

    <label>пароль:</label><br>
    <input type="password" name="password" value="${password}" required><br><br>

    <button type="submit">войти</button>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
</form>
<p>
    нет аккаунта?
    <a href="/register">зарегистрироваться</a>
</p>
</div>
</body>
</html>
