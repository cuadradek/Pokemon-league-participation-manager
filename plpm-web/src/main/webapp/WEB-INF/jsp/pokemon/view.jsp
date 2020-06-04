<%--@author Veronika Loukotova--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:message key="pokemon.info.title" var="title"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
                    <div class="btn-group" role="group" aria-label="Basic example">
                    <c:if test="${pokemon.trainer == null}">
                        <form method="post" action="${pageContext.request.contextPath}/pokemon/catch/${pokemon.id}">
                            <button class="btn btn-detail"><i class="glyphicon glyphicon-camera">
                            <fmt:message key="action.catch"/></i></button>
                        </form>
                    </c:if>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <my:a href="/pokemon/edit/${pokemon.id}" class="btn btn-detail">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        	<fmt:message key="action.edit"/>
                        </my:a>
                    </sec:authorize>
                        <c:if test="${not empty viewOwnPokemon}">
                            <form method="post" action="${pageContext.request.contextPath}/pokemon/train/${pokemon.id}">
                                <button class="btn btn-detail">
                                    <span class="glyphicon glyphicon-circle-arrow-up"/>
                                    <fmt:message key="action.train"/>
                                </button>
                            </form>
                        </c:if>
                    </div>
<table class="table" style="width: 50%">
    <tbody>
        <tr><th>Id</th><td>${pokemon.id}</td></tr>
        <tr><th>Nickname</th><td><c:out value="${pokemon.nickname}"/></td></tr>
        <tr><th>Name</th><td><c:out value="${pokemon.name}"/></td></tr>
        <tr><th>Level</th><td><c:out value="${pokemon.level}"/></td></tr>
        <tr><th>Type</th><td><c:out value="${pokemon.type}"/></td></tr>
        <tr><th>Trainer</th><td><my:a href="/trainer/view/${pokemon.trainer.id}">${pokemon.trainer.nickname}</my:a></td></tr>
    </tbody>
            </table>
    </jsp:attribute>
</my:pagetemplate>

