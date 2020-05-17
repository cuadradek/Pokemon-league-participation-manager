<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:set var="key" value="navigation.gym.edit"/>
<c:if test="${empty editForm.id}">
    <c:set var="key" value="navigation.gym.create"/>
</c:if>

<fmt:message key="${key}" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <p>
            <my:a href="/gym/list" class="btn details-button" role="button">
			    <span class="fa fa-undo" aria-hidden="true"></span>
                <fmt:message key="gym.tolist"/></my:a>
        <c:if test="${not empty editForm.id}">
            <my:a href="/gym/view/${editForm.id}" class="btn details-button" role="button">
			    <span class="fa fa-eye" aria-hidden="true"></span>
                <fmt:message key="action.detail"/></my:a>
        </c:if>
        </p>

    <form:form method="post" action="${pageContext.request.contextPath}/gym/edit"
           modelAttribute="editForm" cssClass="form-horizontal">

        <form:hidden path="id"/>

        <div class="form-group ${city_error?'has-error':''}">
            <form:label path="city" cssClass="col-sm-2 control-label">City</form:label>
            <div class="col-sm-10">
                <form:input path="city" cssClass="form-control"/>
                <form:errors path="city" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
            <div class="col-sm-10">
                <form:select path="type" cssClass="form-control">
                    <form:options items="${types}" />
                </form:select>
            </div>
        </div>

        <div class="form-group ${leader_error?'has-error':''}">
            <form:label path="leader" cssClass="col-sm-2 control-label">Leader id</form:label>

            <div class="col-sm-10">
                <form:input path="leader.id" cssClass="form-control"/>
                <form:errors path="leader.id" cssClass="help-block"/>
            </div>
        </div>

        <button class="btn create-button" type="submit">
            <span class="fa fa-floppy-o" aria-hidden="true"></span>
            <fmt:message key="action.save"/>
        </button>
    </form:form>
    </jsp:attribute>
</my:pagetemplate>
