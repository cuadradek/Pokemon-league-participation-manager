<%--@author Radoslav Cerhak--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<my:pagetemplate title="Trainer info">
    <jsp:attribute name="body">
        <c:choose>
            <c:when test="${not empty viewSelf}">
                <p>
                    <my:a href="/trainer/edit" class="btn detail-button">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        <f:message key="action.edit"/>
                    </my:a>
                </p>
                <p>
                    <my:a href="/trainer/change-password" class="btn detail-button">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        <f:message key="trainer.changepassword"/>
                    </my:a>
                </p>
<%--               <my:a href="/trainer/change-password" class="btn btn-primary">Change password</my:a>--%>
            </c:when>
            <c:otherwise>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <p>
                    <my:a href="/trainer/edit/${trainer.id}" class="btn detail-button">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        <f:message key="action.edit"/>
                    </my:a>
                    </p>
<%--                    <my:a href="/trainer/edit/${trainer.id}" class="btn btn-primary">Edit</my:a>--%>
                </sec:authorize>
            </c:otherwise>
        </c:choose>

        <table style="width: 50%" class="table">
            <tbody>
                <tr><th><f:message key="trainer.id"/></th><td>${trainer.id}</td></tr>
                <tr><th><f:message key="trainer.nickname"/></th><td><c:out value="${trainer.nickname}"/></td></tr>
                <tr><th><f:message key="trainer.firstname"/></th><td><c:out value="${trainer.firstName}"/></td></tr>
                <tr><th><f:message key="trainer.lastname"/></th><td><c:out value="${trainer.lastName}"/></td></tr>
                <tr><th><f:message key="trainer.birthdate"/></th><td><fmt:formatDate value="${trainer.birthDate}" pattern="yyyy-MM-dd"/></td></tr>
                <tr><th><f:message key="trainer.gym"/></th><td><my:a href="/gym/view/${gym.id}">${gym.city}</my:a></td></tr>
                <tr><th><f:message key="trainer.actionpoints"/></th><td><c:out value="${trainer.actionPoints}"/><span class="glyphicon glyphicon-star-empty"></td></tr>
            </tbody>
        </table>

        <h2><f:message key="navigation.pokemons"/></h2>
        <table class="table">
            <thead>
            <tr>
                <th><f:message key="pokemon.id"/></th>
                <th><f:message key="pokemon.nickname"/></th>
                <th><f:message key="pokemon.name"/></th>
                <th><f:message key="pokemon.level"/></th>
                <th><f:message key="pokemon.type"/></th>
                <th><f:message key="pokemon.detail"/></th>
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
                    <td>
                        <my:a href="/pokemon/view/${pokemon.id}" class="btn details-button">
                            <span class="fa fa-eye" aria-hidden="true"></span>
                            <fmt:message key="action.detail"/>
                        </my:a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <h2><f:message key="navigation.badges"/></h2>
        <table style="width: 50%" class="table">
            <thead>
            <tr>
                <th><f:message key="trainer.badgeid"/></th>
                <th><f:message key="trainer.gym"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${badges}" var="badge">
                <tr>
                    <td>${badge.id}</td>
                    <td><my:a href="/gym/view/${badge.gym.id}">${badge.gym.city}</my:a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>
</my:pagetemplate>
