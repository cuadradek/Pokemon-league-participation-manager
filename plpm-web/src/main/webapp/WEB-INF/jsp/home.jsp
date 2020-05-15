<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<%--
  Created by IntelliJ IDEA.
  User: radoslav
  Date: 1. 5. 2020
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<fmt:message key="navigation.personalpage" var="title"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<my:pagetemplate title="Wellcome to PLPM">
    <jsp:attribute name="body">
        Hello world!
    <!my:a href="/trainer/login">Login<!/my:a><!br>
    <!my:a href="/trainer/register">Register<!/my:a><!br>
    <!my:a href="/trainer/list">List<!/my:a><!br>
</jsp:attribute>
</my:pagetemplate>
