<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>

<%--TODO: this will be change once page template is created --%>

<html>
<head>
    <title>Trainers</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

</head>
<body>
<c:if test="${not empty alert_success}">
    <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
</c:if>
<c:if test="${not empty alert_warning}">
    <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
</c:if>

<form:form method="post" action="${pageContext.request.contextPath}/trainer/login"
           modelAttribute="loginForm" cssClass="form-horizontal">


    <div class="form-group ${nickname_error?'has-error':''}">
        <form:label path="nickname" cssClass="col-sm-2 control-label">Nickname</form:label>
        <div class="col-sm-10">
            <form:input path="nickname" cssClass="form-control"/>
            <form:errors path="nickname" cssClass="help-block"/>
        </div>
    </div>

    </div>

    <div class="form-group ${password_error?'has-error':''}">
        <form:label path="nickname" cssClass="col-sm-2 control-label">Password</form:label>
        <div class="col-sm-10">
            <form:password path="password" cssClass="form-control"/>
            <form:errors path="password" cssClass="help-block"/>
        </div>
    </div>

    <button class="btn btn-primary" type="submit">Login</button>
</form:form>

</body>
</html>
