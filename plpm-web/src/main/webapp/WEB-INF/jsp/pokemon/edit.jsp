<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="titletext" value="Edit pokemon"/>
<c:set var="keytext" value="navigation.pokemon.edit"/>
<c:if test="${empty editForm.id}">
    <c:set var="titletext" value="Create pokemon"/>
    <c:set var="keytext" value="navigation.pokemon.create"/>
</c:if>

<fmt:message key="${keytext}" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="${titletext}">
    <jsp:attribute name="body">
        <p>
        <my:a href="/pokemon/list" class="btn btn-basic" role="button"><fmt:message key="pokemon.tolist"/></my:a>
        <c:if test="${not empty editForm.id}">
            <my:a href="/pokemon/view/${editForm.id}" class="btn btn-basic" role="button"><fmt:message key="gym.detail"/></my:a>
        </c:if>
        </p>

    <form:form method="post" action="${pageContext.request.contextPath}/pokemon/edit"
           modelAttribute="editForm" cssClass="form-horizontal">

        <form:hidden path="id"/>

        <div class="form-group ${nickname_error?'has-error':''}">
            <form:label path="nickname" cssClass="col-sm-2 control-label">Nickname</form:label>
            <div class="col-sm-10">
                <form:input path="nickname" cssClass="form-control"/>
                <form:errors path="nickname" cssClass="help-block"/>
            </div>
        </div>


        <div class="form-group">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>

            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>

         <sec:authorize access="hasRole('ROLE_ADMIN')">
        <c:if test="${not empty editForm.id}">
        <div class="form-group">
            <form:label path="level" cssClass="col-sm-2 control-label">Level</form:label>
                    <div class="col-sm-10">
                        <form:input path="level" cssClass="form-control"/>
                        <form:errors path="level" cssClass="help-block"/>
                    </div>
                </div>
              </c:if>
              </sec:authorize>

        <div class="form-group">
            <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
            <div class="col-sm-10">
                <form:select path="type" cssClass="form-control">
                    <form:options items="${types}" />
                </form:select>
            </div>
        </div>

        <button class="btn btn-success" type="submit">Save</button>
    </form:form>
    </jsp:attribute>
</my:pagetemplate>
