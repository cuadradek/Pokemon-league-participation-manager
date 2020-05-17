<%--@author Radoslav Cerhak--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="Change password">
    <jsp:attribute name="body">

        <form:form method="post" action="${pageContext.request.contextPath}/trainer/change-password"
                   modelAttribute="passwordForm" cssClass="form-horizontal">

            <form:hidden path="id"/>

            <div class="form-group ${oldPassword_error?'has-error':''}">
                <form:label path="oldPassword" cssClass="col-sm-2 control-label"><f:message key="trainer.oldpassword"/></form:label>
                <div class="col-sm-10">
                    <form:password path="oldPassword" cssClass="form-control"/>
                    <form:errors path="oldPassword" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${newPassword_error?'has-error':''}">
                <form:label path="newPassword" cssClass="col-sm-2 control-label"><f:message key="trainer.newpassword"/></form:label>
                <div class="col-sm-10">
                    <form:password path="newPassword" cssClass="form-control"/>
                    <form:errors path="newPassword" cssClass="help-block"/>
                </div>
            </div>

            <button class="btn details-button" type="submit"><f:message key="trainer.changepassword"/></button>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>