<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:message key="navigation.gyms" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="Gyms">
    <jsp:attribute name="body">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        <p>
		<my:a href="/gym/create" class="btn create-button">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			<fmt:message key="gym.create"/>
		</my:a>
        </p></sec:authorize>

	<table class="table table-align-left table-striped">
        <thead>
        <tr>
            <th class="col-xs-1 col-md-1 col-lg-1"><fmt:message key="gym.id"/></th>
            <th><fmt:message key="gym.city"/></th>
            <th><fmt:message key="gym.leader"/></th>
            <th><fmt:message key="gym.type"/></th>
            <th><fmt:message key="gym.actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${gyms}" var="gym">
				<tr>
                    <td>${gym.id}</td>
                    <td>${gym.city}</td>
                    <td><my:a href="/trainer/view/${gym.leader.id}">${gym.leader.nickname}</my:a></td>
                    <td>${gym.type}</td>
                    <td>
                        <div class="btn-group" role="group">

                            <my:a href="/gym/view/${gym.id}" class="btn details-button">
			                    <span class="fa fa-eye" aria-hidden="true"></span>
			                    <fmt:message key="action.detail"/>
		                    </my:a>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <form method="post" action="${pageContext.request.contextPath}/gym/delete/${gym.id}">
                                    <button class="btn delete-button"><i class="fa fa-trash"> <fmt:message key="action.delete"/></i></button>
                                </form>
                            </sec:authorize>

                        </div>
                    </td>
                </tr>
			</c:forEach>
        </tbody>
    </table>


    </jsp:attribute>
</my:pagetemplate>