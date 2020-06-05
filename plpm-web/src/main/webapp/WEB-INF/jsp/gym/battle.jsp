<%--@author Jakub Doczy--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:message key="gym.battle" var="title"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <form method="post">
            <table class="table">
                <thead>
                    <tr>
                        <th><fmt:message key="action.select"/></th>
                        <th><fmt:message key="pokemon.nickname"/></th>
                        <th><fmt:message key="pokemon.name"/></th>
                        <th><fmt:message key="pokemon.level"/></th>
                        <th><fmt:message key="pokemon.type"/></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${trainersPokemons}" var="pokemon">
                    <tr>
                        <td>
                            <label>
                                <input type="checkbox" name="selected" value="${pokemon.id}">
                            </label>
                        </td>
                        <td><c:out value="${pokemon.nickname}"/></td>
                        <td><c:out value="${pokemon.name}"/></td>
                        <td><c:out value="${pokemon.level}"/></td>
                        <td><c:out value="${pokemon.type}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <button class="btn btn-primary" type="submit">
                <fmt:message key="gym.attack"/>
            </button>
        </form>


    </jsp:attribute>
</my:pagetemplate>