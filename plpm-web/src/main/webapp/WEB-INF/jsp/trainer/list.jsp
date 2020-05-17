<%--@author Radoslav Cerhak--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<my:pagetemplate title="Trainers">
    <jsp:attribute name="body">

        <table class="table table-align-left table-striped">
            <thead>
            <tr>
                <th><f:message key="trainer.id"/></th>
                <th><f:message key="trainer.nickname"/></th>
                <th><f:message key="trainer.firstname"/></th>
                <th><f:message key="trainer.lastname"/></th>
                <th><f:message key="trainer.birthdate"/></th>
                <th><f:message key="trainer.detail"/></th>
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
<%--                        <my:a href="/trainer/view/${trainer.id}" class="btn"><i class="fa fa-eye"></my:a>--%>
                        <form method="get" action="${pageContext.request.contextPath}/trainer/view/${trainer.id}">
                            <button class="btn"><i class="fa fa-eye"></i></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>

