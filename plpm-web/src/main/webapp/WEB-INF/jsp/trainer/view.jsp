<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

</head>
<body>
<table class="table">
    <caption>Trainer info</caption>
    <tbody>
        <tr><th>Id</th><td>${trainer.id}</td></tr>
        <tr><th>Nickname</th><td><c:out value="${trainer.nickname}"/></td></tr>
        <tr><th>First name</th><td><c:out value="${trainer.firstName}"/></td></tr>
        <tr><th>Last name</th><td><c:out value="${trainer.lastName}"/></td></tr>
        <tr><th>Birth date</th><td><fmt:formatDate value="${trainer.birthDate}" pattern="yyyy-MM-dd"/></td></tr>
        <tr><th>Gym</th><td><my:a href="/gym/view/${gym.id}">${gym.id}</my:a></td></tr>
    </tbody>
</table>

<table class="table">
    <caption>Pokemons</caption>
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

<table class="table">
    <caption>Beaten Gyms</caption>
    <thead>
    <tr>
        <th>Id</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${badges}" var="badge">
        <tr>
            <td><my:a href="/gym/view/${gym.id}">${gym.id}</my:a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
