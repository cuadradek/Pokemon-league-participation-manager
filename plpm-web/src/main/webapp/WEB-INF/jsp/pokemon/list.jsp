<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--TODO: this will be change once page template is created --%>

<fmt:message key="navigation.badges" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="Pokemons">
    <jsp:attribute name="body">
        <p>
		<my:a href="/pokemon/create" class="btn btn-success">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			<fmt:message key="badge.create"/>
		</my:a>
        </p>

<table class="table">
    <thead>
    <tr>
        <th>Id</th>
        <th>Nickname</th>
        <th>Name</th>
        <th>Level</th>
        <th>Type</th>
        <th>Trainer</th>
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
                    <my:a href="/pokemon/view/${pokemon.id}" class="btn btn-primary">Detail</my:a>
                    <c:if test="${pokemon.trainer == null}">
                        <my:a href="/pokemon/catch/${pokemon.id}" class="btn btn-primary">Catch</my:a>
                    </c:if>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
    </jsp:attribute>
</my:pagetemplate>

