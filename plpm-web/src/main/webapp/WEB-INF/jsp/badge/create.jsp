<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:message key="badge.create.title" var="title"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <form:form method="post" action="${pageContext.request.contextPath}/badge/create" modelAttribute="badgeCreate"
                   cssClass="form-horizontal">
		    <div class="form-group ${trainer_error ? 'has-error' : ''}">
			    <form:label path="trainerId" cssClass="col-sm-2 control-label">
				    <fmt:message key="badge.trainer"/>
			    </form:label>
                <div class="col-sm-10">
				    <form:select path="trainerId" cssClass="form-control">
					    <c:forEach items="${trainers}" var="trainer">
						    <form:option value="${trainer.id}">${trainer.nickname}</form:option>
					    </c:forEach>
				    </form:select>
                    <form:errors path="trainerId" cssClass="help-block"/>
                </div>
            </div>

		    <div class="form-group">
			    <form:label path="gymId" cssClass="col-sm-2 control-label">
				    <fmt:message key="badge.gym"/>
			    </form:label>
                <div class="col-sm-10">
				    <form:select path="gymId" cssClass="form-control">
					    <c:forEach items="${gyms}" var="gym">
						    <form:option value="${gym.id}">${gym.city}</form:option>
					    </c:forEach>
				    </form:select>
                    <form:errors path="gymId" cssClass="error"/>
                </div>
            </div>

		    <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-primary" type="submit">
                        <fmt:message key="badge.create"/>
                    </button>
                </div>
            </div>
	    </form:form>
    </jsp:attribute>
</my:pagetemplate>
