<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<my:pagetemplate title="Error">
    <jsp:attribute name="body">
        <h1><f:message key="error.notfound"/></h1>
    </jsp:attribute>
</my:pagetemplate>
