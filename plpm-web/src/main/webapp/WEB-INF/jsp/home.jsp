<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<%--
  Created by IntelliJ IDEA.
  User: radoslav
  Date: 1. 5. 2020
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${not empty alert_success}">
    <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
</c:if>
<c:if test="${not empty alert_warning}">
    <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
</c:if>
Hello world!<br>
Available pages:<br>
<my:a href="/trainer/login">Login</my:a><br>
<my:a href="/trainer/register">Register</my:a><br>
<my:a href="/trainer/list">List</my:a><br>
</body>
</html>
