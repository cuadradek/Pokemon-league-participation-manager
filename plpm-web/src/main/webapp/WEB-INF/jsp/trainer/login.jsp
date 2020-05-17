<%--@author Radoslav Cerhak--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="Login">
    <jsp:attribute name="body">

<form:form method="post" action="${pageContext.request.contextPath}/login"
           cssClass="form-horizontal">

    <div class="form-group">
        <label cssClass="col-sm-2 control-label"><f:message key="trainer.nickname"/></label>
        <div class="col-sm-10">
            <input name="nickname" id="nickname" cssClass="form-control"/>
        </div>
    </div>

    <div class="form-group">
        <label cssClass="col-sm-2 control-label"><f:message key="trainer.password"/></label>
        <div class="col-sm-10">
            <input type="password" name="password" id="password" cssClass="form-control"/>
        </div>
    </div>

    <button class="btn btn-primary" type="submit"><f:message key="navigation.login"/></button>
</form:form>

    </jsp:attribute>
</my:pagetemplate>