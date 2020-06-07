<%--@author Veronika Loukotova--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:message key="navigation.pokemon" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="Pokemons">
    <jsp:attribute name="body">
        <p>
		<my:a href="/pokemon/create" class="btn create-button">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			<fmt:message key="pokemon.create"/>
		</my:a>
        </p>

<table class="table">
    <thead>
    <tr>
        <th><fmt:message key="pokemon.id"/></th>
        <th><fmt:message key="pokemon.nickname"/></th>
                        <th><fmt:message key="pokemon.name"/></th>
                        <th><fmt:message key="pokemon.level"/></th>
                        <th><fmt:message key="pokemon.type"/></th>
                        <th><fmt:message key="pokemon.trainer"/></th>
                        <th><fmt:message key="pokemon.actions"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${pokemons}" var="pokemon">
        <tr>
            <td>${pokemon.id}</td>
            <td><c:out value="${pokemon.nickname}"/></td>
            <td><c:out value="${pokemon.name}"/></td>
            <td><c:out value="${pokemon.level}"/></td>
            <td><c:out value="${pokemon.type}"/></td>
            <td><my:a href="/trainer/view/${pokemon.trainer.id}">${pokemon.trainer.nickname}</my:a></td>
            <td>
                <div class="btn-group" role="group" aria-label="Basic example">
                    <my:a href="/pokemon/view/${pokemon.id}" class="btn details-button">
                    			                    <span class="fa fa-eye" aria-hidden="true"></span>
                    			                    <fmt:message key="action.detail"/>
                    		                    </my:a>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <form method="post" action="${pageContext.request.contextPath}/pokemon/delete/${pokemon.id}">
                            <button class="btn delete-button"><i class="fa fa-eye"> <fmt:message key="action.delete"/></i></button>
                        </form>
                    </sec:authorize>
                    <c:if test="${pokemon.trainer == null}">
                        <form method="post" action="${pageContext.request.contextPath}/pokemon/catch/${pokemon.id}">
                            <button class="btn details-button"><i class="glyphicon glyphicon-camera">
                            <fmt:message key="action.catch"/></i></button>
                        </form>
                    </c:if>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
    </jsp:attribute>
</my:pagetemplate>

