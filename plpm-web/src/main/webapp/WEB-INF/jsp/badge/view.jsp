<%--@author Jakub Doczy--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:message key="badge.info.title" var="title"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <table style="width: 50%" class="table">
            <tbody>
                <tr><th><fmt:message key="badge.id"/></th><td>${badge.id}</td></tr>
                <tr><th><fmt:message key="badge.trainer"/></th><td><my:a href="/trainer/view/${badge.trainer.id}">${badge.trainer.nickname}</my:a></td></tr>
                <tr><th><fmt:message key="badge.gym"/></th><td><my:a href="/gym/view/${badge.gym.id}">${badge.gym.city}</my:a></td></tr>
            </tbody>
        </table>

        <h2><fmt:message key="badge.trainer"/></h2>

        <my:a href="/trainer/view/${badge.trainer.id}" class="btn details-button">
		    <span class="fa fa-eye" aria-hidden="true"></span>
			<fmt:message key="action.detail"/>
        </my:a>

        <table style="width: 50%" class="table">
            <tbody>
                <tr><th><fmt:message key="trainer.id"/></th><td>${badge.trainer.id}</td></tr>
                <tr><th><fmt:message key="trainer.nickname"/></th><td>${badge.trainer.nickname}</td></tr>
                <tr><th><fmt:message key="trainer.firstName"/></th><td>${badge.trainer.firstName}</td></tr>
                <tr><th><fmt:message key="trainer.lastName"/></th><td>${badge.trainer.lastName}</td></tr>
                <tr><th><fmt:message key="trainer.birthDate"/></th><td><fmt:formatDate value="${badge.trainer.birthDate}" pattern="yyyy-MM-dd"/></td></tr>
                <c:if test="${trainersGym != null}">
                    <tr><th><fmt:message key="trainer.gym"/></th><td><my:a href="/gym/view/${trainersGym.id}">${trainersGym.city}</my:a></td></tr>
                </c:if>
            </tbody>
        </table>

        <h2><fmt:message key="badge.gym"/></h2>
        <my:a href="/gym/view/${badge.gym.id}" class="btn details-button">
		    <span class="fa fa-eye" aria-hidden="true"></span>
			<fmt:message key="action.detail"/>
        </my:a>

        <table style="width: 50%" class="table">
            <tbody>
                <tr><th><fmt:message key="gym.id"/></th><td>${badge.trainer.id}</td></tr>
                <tr><th><fmt:message key="gym.leader"/></th><td><my:a href="/trainer/view/${badge.gym.leader.id}">${badge.gym.leader.nickname}</my:a></td></tr>
                <tr><th><fmt:message key="gym.city"/></th><td>${badge.gym.city}</td></tr>
                <tr><th><fmt:message key="gym.type"/></th><td>${badge.gym.type}</td></tr>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
