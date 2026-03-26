<%--
  Created by IntelliJ IDEA.
  User: asilyashakirova
  Date: 26.03.2026
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newspaper.css">
    <title>ошибка</title>
</head>
<body>
<div class="container">
<h2>произошла ошибка</h2>

<p>${message}</p>

<a href="/feed">вернуться к ленте</a>
</div>
</body>
</html>
