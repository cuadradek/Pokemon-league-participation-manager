<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message key="navigation.gyms" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="Gyms">
    <jsp:attribute name="body">
        <!-- TODO: hide button for not logged in user -->
        <p>
		<my:a href="/gym/create" class="btn btn-success">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			<fmt:message key="gym.create"/>
		</my:a>
        </p>

	<table class="table table-align-left table-striped">
        <thead>
        <tr>
            <th class="col-xs-1 col-md-1 col-lg-1"><fmt:message key="gym.id"/></th>
            <th><fmt:message key="gym.city"/></th>
            <th><fmt:message key="gym.leader"/></th>
            <th><fmt:message key="gym.type"/></th>
            <th><fmt:message key="gym.detail"/></th>
            <!-- TODO: try to hide from non admin -->
            <th><fmt:message key="gym.delete"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${gyms}" var="gym">
				<tr>
                    <td>${gym.id}</td>
                    <td>${gym.city}</td>
                    <td>${gym.leader.firstName} ${gym.leader.lastName}</td>
                    <td>${gym.type}</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/gym/view/${gym.id}">
                            <button class="btn"><i class="fa fa-eye"></i></button>
                        </form>
                    </td>
                    <!-- TODO: try to hide from non admin -->
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/gym/delete/${gym.id}">
                            <button class="btn"><i class="fa fa-trash"></i></button>
                        </form>
                    </td>
                </tr>
			</c:forEach>
        </tbody>
    </table>


    </jsp:attribute>
</my:pagetemplate>