<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--TODO: this will be change once page template is created --%>

<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

</head>
<body>

<form:form method="post" action="${pageContext.request.contextPath}/trainer/register"
           modelAttribute="trainerForm" cssClass="form-horizontal">


    <div class="form-group ${nickname_error?'has-error':''}">
        <form:label path="nickname" cssClass="col-sm-2 control-label">Nickname</form:label>
        <div class="col-sm-10">
            <form:input path="nickname" cssClass="form-control"/>
            <form:errors path="nickname" cssClass="help-block"/>
        </div>
    </div>

    <div class="form-group ${firstName_error?'has-error':''}">
        <form:label path="firstName" cssClass="col-sm-2 control-label">First name</form:label>
        <div class="col-sm-10">
            <form:input path="firstName" cssClass="form-control"/>
            <form:errors path="firstName" cssClass="help-block"/>
        </div>
    </div>

    <div class="form-group ${lastName_error?'has-error':''}">
        <form:label path="lastName" cssClass="col-sm-2 control-label">Last name</form:label>
        <div class="col-sm-10">
            <form:input path="lastName" cssClass="form-control"/>
            <form:errors path="lastName" cssClass="help-block"/>
        </div>
    </div>

    <div class="form-group ${password_error?'has-error':''}">
        <form:label path="nickname" cssClass="col-sm-2 control-label">Password</form:label>
        <div class="col-sm-10">
            <form:password path="password" cssClass="form-control"/>
            <form:errors path="password" cssClass="help-block"/>
        </div>
    </div>

    <div class="form-group ${birthDate_error?'has-error':''}">
        <form:label path="birthDate" cssClass="col-sm-2 control-label">Birth date</form:label>
        <div class="col-sm-10">
            <form:input type="date" path="birthDate" cssClass="form-control"/>
            <form:errors path="birthDate" cssClass="help-block"/>
        </div>
    </div>

    <button class="btn btn-primary" type="submit">Register</button>
</form:form>

</body>
</html>
