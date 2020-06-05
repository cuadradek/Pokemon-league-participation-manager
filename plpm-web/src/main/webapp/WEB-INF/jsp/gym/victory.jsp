<%--@author Jakub Doczy--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:message key="gym.victory" var="title"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h3>
            <fmt:message key="gym.victory.text"/>
        </h3>
        <h3><my:a href="/badge/view/${badgeId}"> <fmt:message key="badge.title.singular"/></my:a></h3>
    </jsp:attribute>
</my:pagetemplate>