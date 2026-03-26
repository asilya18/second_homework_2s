<%--
  Created by IntelliJ IDEA.
  User: asilyashakirova
  Date: 24.03.2026
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newspaper.css">
    <meta charset="UTF-8">
    <title>регистрация</title>
</head>
<body>
<div class="container">
<h2>регистрация</h2>

<!-- для глобальных ошибок всей формы если они есть -->
<c:if test="${not empty globalError}">
    <div style="color: red; margin-bottom: 10px;">
            ${globalError}
    </div>
</c:if>

<form:form modelAttribute="form" method="post" action="${pageContext.request.contextPath}/register">
    <div>
        <label>email:</label><br>
        <form:input path="email"/>
        <form:errors path="email" cssStyle="color:red;"/>
    </div>

    <br>

    <div>
        <label>username:</label><br>
        <form:input path="username"/>
        <form:errors path="username" cssStyle="color:red;"/>
    </div>

    <br>
    <div>
        <label>пароль:</label><br>
        <form:password path="password" value="${form.password}"/>
        <form:errors path="password" cssStyle="color:red;"/>
    </div>
    <br>
    <div>
        <label>подтверждение пароля:</label><br>
        <form:password path="confirmPassword" value="${form.confirmPassword}"/>
        <form:errors path="confirmPassword" cssStyle="color:red;"/>
    </div>

    <br>

    <div>
        <input type="submit" value="зарегистрироваться">
    </div>

</form:form>
</div>
</body>
</html>
