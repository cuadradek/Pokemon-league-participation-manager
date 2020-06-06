<%--@author Jakub Doczy--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:message key="gym.loss" var="title"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h3>
            <fmt:message key="gym.loss.text"/>
        </h3>
    </jsp:attribute>
</my:pagetemplate>