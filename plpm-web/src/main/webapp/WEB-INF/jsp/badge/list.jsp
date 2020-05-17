<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>




<fmt:message key="badge.title.plural" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <!-- TODO: hide button for not logged in user -->
        <p>
		<my:a href="/badge/create" class="btn create-button">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			<fmt:message key="badge.create"/>
		</my:a>
        </p>

	<table class="table table-align-left table-striped">
        <thead>
        <tr>
            <th class="col-xs-1 col-md-1 col-lg-1"><fmt:message key="badge.id"/></th>
            <th><fmt:message key="badge.trainer"/></th>
            <th><fmt:message key="badge.gym"/></th>

            <!-- TODO: try to hide from non admin -->
            <th><fmt:message key="badge.actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${badges}" var="badge">
				<tr>
                    <td>${badge.id}</td>
                    <td><my:a href="/trainer/view/${badge.trainer.id}">${badge.trainer.nickname}</my:a></td>
                    <td><my:a href="/gym/view/${badge.gym.id}">${badge.gym.city}</my:a></td>
                    <td>
                        <div class="btn-group" role="group">

                            <my:a href="/badge/view/${badge.id}" class="btn details-button">
			                    <span class="fa fa-eye" aria-hidden="true"></span>
			                    <fmt:message key="action.detail"/>
		                    </my:a>
                            <!-- TODO: try to hide from non admin -->
                            <my:a href="/badge/delete/${badge.id}" class="btn delete-button">
			                    <span class="fa fa-trash" aria-hidden="true"></span>
			                    <fmt:message key="action.delete"/>
		                    </my:a>
                        </div>
                    </td>
                </tr>
			</c:forEach>
        </tbody>
    </table>
    </jsp:attribute>
</my:pagetemplate>
