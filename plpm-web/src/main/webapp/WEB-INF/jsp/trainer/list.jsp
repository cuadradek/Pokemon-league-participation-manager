<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--TODO: this will be change once page template is created --%>

<html>
<head>
    <title>Trainers</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

</head>
<body>

<table class="table">
    <caption>Trainers</caption>
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
                 <div class="btn-group" role="group" aria-label="Basic example">
                                <my:a href="/trainer/view/${trainer.id}" class="btn btn-primary">Detail</my:a>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <my:a href="/trainer/edit/${trainer.id}" class="btn btn-primary">Edit</my:a>
                                </sec:authorize>
                                <my:a href="/trainer/edit/${trainer.id}" class="btn btn-primary">Edit</my:a>
                            </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
