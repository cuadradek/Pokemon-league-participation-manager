<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<my:pagetemplate title="Trainer info">
    <jsp:attribute name="body">
        <c:choose>
            <c:when test="${not empty viewSelf}">
               <my:a href="/trainer/edit" class="btn btn-primary">Edit</my:a>
               <my:a href="/trainer/change-password" class="btn btn-primary">Change password</my:a>
            </c:when>
            <c:otherwise>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <my:a href="/trainer/edit/${trainer.id}" class="btn btn-primary">Edit</my:a>
                </sec:authorize>
            </c:otherwise>
        </c:choose>

        <table style="width: 50%" class="table">
            <tbody>
                <tr><th>Id</th><td>${trainer.id}</td></tr>
                <tr><th>Nickname</th><td><c:out value="${trainer.nickname}"/></td></tr>
                <tr><th>First name</th><td><c:out value="${trainer.firstName}"/></td></tr>
                <tr><th>Last name</th><td><c:out value="${trainer.lastName}"/></td></tr>
                <tr><th>Birth date</th><td><fmt:formatDate value="${trainer.birthDate}" pattern="yyyy-MM-dd"/></td></tr>
                <tr><th>Gym</th><td><my:a href="/gym/view/${gym.id}">${gym.city}</my:a></td></tr>
            </tbody>
        </table>

        <h2>Pokemons</h2>
        <table class="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Nickname</th>
                <th>Name</th>
                <th>Level</th>
                <th>Type</th>
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
                        <my:a href="/pokemon/view/${pokemon.id}" class="btn btn-primary">Detail</my:a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <h2>Badges</h2>
        <table style="width: 50%" class="table">
            <thead>
            <tr>
                <th>Badge id</th>
                <th>Gym</th>
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
