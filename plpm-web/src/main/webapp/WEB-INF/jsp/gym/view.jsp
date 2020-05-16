<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<fmt:message key="navigation.gym.detail" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="Gym detail">
    <jsp:attribute name="body">
        <!-- TODO: hide button for non admin/gym leader -->
        <p>
		<my:a href="/gym/edit/${gym.id}" class="btn btn-success">
			<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
			<fmt:message key="gym.edit"/>
		</my:a>
        </p>


	    <table class="table table-align-left" style="width: 50%">
            <tbody>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="gym.id"/></th><td class="col-xs-3 col-md-3 col-lg-3">${gym.id}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="gym.city"/></th><td class="col-xs-3 col-md-3 col-lg-3">${gym.city}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="gym.type"/></th><td class="col-xs-3 col-md-3 col-lg-3">${gym.type}</td></tr>
            </tbody>
        </table>

        <h3><fmt:message key="gym.leader"/></h3>
        <p>
        <form method="get" action="${pageContext.request.contextPath}/trainer/view/${gym.leader.id}">
            <button class="btn btn-success"><i class="fa fa-eye"></i> <fmt:message key="gym.detail"/></button>
        </form>
        </p>
        <table class="table table-align-left" style="width: 50%">
            <tbody>
            <tr><th class="col-xs-2 col-md-1 col-lg-1"><fmt:message key="gym.id"/></th><td>${gym.leader.id}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1">Nickname</th><td class="col-xs-3 col-md-3 col-lg-2">${gym.leader.nickname}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1">First name</th><td class="col-xs-3 col-md-3 col-lg-2">${gym.leader.firstName}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1">Last name</th><td class="col-xs-3 col-md-3 col-lg-2">${gym.leader.lastName}</td></tr>
            <tr><th class="col-xs-2 col-md-1 col-lg-1">Birth date</th><td class="col-xs-3 col-md-3 col-lg-2"><fmt:formatDate value="${gym.leader.birthDate}" pattern="yyyy-MM-dd"/></td></tr>
            </tbody>
        </table>

        <h3><fmt:message key="gym.badges"/></h3>
	<table class="table table-align-left">
        <thead>
        <tr>
            <th class="col-xs-1 col-md-1 col-lg-1"><fmt:message key="badge.id"/></th>
            <th class="col-xs-3 col-md-2 col-lg-2"><fmt:message key="badge.trainer"/></th>
            <th class="col-xs-1 col-md-1 col-lg-1"><fmt:message key="gym.detail"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${badges}" var="badge">
				<tr>
                    <td>${badge.id}</td>
                    <td>${badge.trainer.firstName} ${badge.trainer.lastName}</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/badge/view/${badge.id}">
                            <button class="btn btn-sm"><i class="fa fa-eye"></i></button>
                        </form>
                    </td>
                </tr>
			</c:forEach>
        </tbody>
    </table>


    </jsp:attribute>
</my:pagetemplate>
