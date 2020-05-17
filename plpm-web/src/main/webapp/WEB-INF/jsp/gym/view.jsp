<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<fmt:message key="navigation.gym.detail" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <%-- Only admin or gym leader can edit--%>
        <c:if test="${not empty canEdit}">
        <p>
		<my:a href="/gym/edit/${gym.id}" class="btn details-button">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
			<fmt:message key="action.edit"/>
		</my:a>
        </p>
        </c:if>

	    <table class="table table-align-left" style="width: 50%">
            <tbody>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="gym.id"/></th><td class="col-xs-3 col-md-3 col-lg-3">${gym.id}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="gym.city"/></th><td class="col-xs-3 col-md-3 col-lg-3">${gym.city}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="gym.type"/></th><td class="col-xs-3 col-md-3 col-lg-3">${gym.type}</td></tr>
            </tbody>
        </table>

        <h2><fmt:message key="gym.leader"/></h2>

        <my:a href="/trainer/view/${gym.leader.id}" class="btn details-button">
		    <span class="fa fa-eye" aria-hidden="true"></span>
			<fmt:message key="action.detail"/>
        </my:a>

        <table class="table" style="width: 50%">
            <tbody>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="trainer.id"/></th><td>${gym.leader.id}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="trainer.nickname"/></th><td class="col-xs-3 col-md-3 col-lg-2">${gym.leader.nickname}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="trainer.firstName"/></th><td class="col-xs-3 col-md-3 col-lg-2">${gym.leader.firstName}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="trainer.lastName"/></th><td class="col-xs-3 col-md-3 col-lg-2">${gym.leader.lastName}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="trainer.birthDate"/></th><td class="col-xs-3 col-md-3 col-lg-2"><fmt:formatDate value="${gym.leader.birthDate}" pattern="yyyy-MM-dd"/></td></tr>
            </tbody>
        </table>

        <h2><fmt:message key="gym.badges"/></h2>
	<table class="table" style="width: 50%">
        <thead>
        <tr>
            <th class="col-xs-1 col-md-1 col-lg-1"><fmt:message key="badge.id"/></th>
            <th class="col-xs-3 col-md-2 col-lg-2"><fmt:message key="badge.trainer"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${badges}" var="badge">
				<tr>
                    <td>${badge.id}</td>
                    <td>${badge.trainer.firstName} ${badge.trainer.lastName}</td>
                    <td>
                        <my:a href="/badge/view/${badge.id}" class="btn details-button">
		                    <span class="fa fa-eye" aria-hidden="true"></span>
			                <fmt:message key="action.detail"/>
                        </my:a>
                    </td>
                </tr>
			</c:forEach>
        </tbody>
    </table>


    </jsp:attribute>
</my:pagetemplate>
