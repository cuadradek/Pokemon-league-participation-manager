<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:pagetemplate title="Trainers">
    <jsp:attribute name="body">

        <table class="table table-align-left table-striped">
            <thead>
            <tr>
                <th>Id</th>
                <th>Nickname</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Birth date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${trainers}" var="trainer">
                <tr>
                    <td>${trainer.id}</td>
                    <td><c:out value="${trainer.nickname}"/></td>
                    <td><c:out value="${trainer.firstName}"/></td>
                    <td><c:out value="${trainer.lastName}"/></td>
                    <td><fmt:formatDate value="${trainer.birthDate}" pattern="yyyy-MM-dd"/></td>
                    <td>
                        <my:a href="/trainer/view/${trainer.id}" class="btn btn-primary">Detail</my:a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>
