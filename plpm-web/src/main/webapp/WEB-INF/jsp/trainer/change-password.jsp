<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

</head>
<body>

<h2>Change password</h2>
<form:form method="post" action="${pageContext.request.contextPath}/trainer/change-password"
           modelAttribute="passwordForm" cssClass="form-horizontal">

    <form:hidden path="id"/>

    <div class="form-group ${oldPassword_error?'has-error':''}">
        <form:label path="oldPassword" cssClass="col-sm-2 control-label">Old password</form:label>
        <div class="col-sm-10">
            <form:password path="oldPassword" cssClass="form-control"/>
            <form:errors path="oldPassword" cssClass="help-block"/>
        </div>
    </div>

    <div class="form-group ${newPassword_error?'has-error':''}">
        <form:label path="newPassword" cssClass="col-sm-2 control-label">New password</form:label>
        <div class="col-sm-10">
            <form:password path="newPassword" cssClass="form-control"/>
            <form:errors path="newPassword" cssClass="help-block"/>
        </div>
    </div>

    <button class="btn btn-primary" type="submit">Change password</button>
</form:form>
</body>
</html>
