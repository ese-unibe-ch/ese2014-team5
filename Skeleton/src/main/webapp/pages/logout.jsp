<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page session="true"%>


<c:import url="template/header.jsp" />

<div class="booking_room">
			<h4>Title : ${title}</h4>
			
		</div>
		<div class="reservation">

<c:url value="/j_spring_security_logout" var="logoutUrl" />

<form:form method="post" modelAttribute="logoutForm" action="${logoutUrl}" id="logoutForm" cssClass="form-horizontal"
           autocomplete="off">
    <input type="hidden" 
		name="${_csrf.parameterName}"
		value="${_csrf.token}" />
</form:form>

<script>
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>

<c:if test="${pageContext.request.userPrincipal.name != null}">
	<h2>
		Welcome : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()">Logout</a>
	</h2>
</c:if>
</div>
		</div>
		<div class="clear"></div>
		</div>

<c:import url="template/footer.jsp" />
