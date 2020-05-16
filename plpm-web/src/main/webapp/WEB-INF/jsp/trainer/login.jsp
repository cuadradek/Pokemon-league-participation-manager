<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>

<my:pagetemplate title="Login">
    <jsp:attribute name="body">

<form:form method="post" action="${pageContext.request.contextPath}/trainer/login"
           modelAttribute="loginForm" cssClass="form-horizontal">


    <div class="form-group ${nickname_error?'has-error':''}">
        <form:label path="nickname" cssClass="col-sm-2 control-label">Nickname</form:label>
        <div class="col-sm-10">
            <form:input path="nickname" cssClass="form-control"/>
            <form:errors path="nickname" cssClass="help-block"/>
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

    </jsp:attribute>
</my:pagetemplate>