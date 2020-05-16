<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pokemon</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

</head>
<body>
<table class="table">
    <caption>Pokemon info</caption>
    <tbody>
        <tr><th>Id</th><td>${pokemon.id}</td></tr>
        <tr><th>Nickname</th><td><c:out value="${pokemon.nickname}"/></td></tr>
        <tr><th>Name</th><td><c:out value="${pokemon.name}"/></td></tr>
        <tr><th>Level</th><td><c:out value="${pokemon.level}"/></td></tr>
        <tr><th>Type</th><td><c:out value="${pokemon.type}"/></td></tr>
        <tr><th>Trainer</th><td><my:a href="/trainer/view/${pokemon.trainer.id}">${pokemon.trainer.nickname}</my:a></td></tr>
        <c:if test="${pokemon.trainer == null}">
            <tr><th><btn class="btn btn-primary">Catch</btn></th></tr>
        </c:if>
    </tbody>
</table>
</body>
</html>
