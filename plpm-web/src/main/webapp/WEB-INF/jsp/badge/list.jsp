<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>




<fmt:message key="navigation.badges" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="Badges">
    <jsp:attribute name="body">
        <!-- TODO: hide button for not logged in user -->
        <p>
		<my:a href="/badge/create" class="btn btn-success">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			<fmt:message key="badge.create"/>
		</my:a>
        </p>

	<table class="table table-align-left table-striped">
        <thead>
        <tr>
            <th class="col-xs-1 col-md-1 col-lg-1"><fmt:message key="badge.id"/></th>
            <th><fmt:message key="badge.trainer"/></th>
            <th><fmt:message key="badge.city"/></th>
            <!-- TODO: try to hide from non admin -->
            <th><fmt:message key="badge.delete"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${badges}" var="badge">
				<tr>
                    <td>${badge.id}</td>
                    <td>${badge.trainer.nickname}</td>
                    <td>${badge.gym.city}</td>
                    <!-- TODO: try to hide from non admin -->
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/badge/delete/${badge.id}">
                            <button class="btn"><i class="fa fa-trash"></i></button>
                        </form>
                    </td>
                </tr>
			</c:forEach>
        </tbody>
    </table>


    </jsp:attribute>
</my:pagetemplate>
