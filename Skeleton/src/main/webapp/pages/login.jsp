<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<div class="booking_room">
			<h4>Login</h4>
			
		</div>
		<div class="reservation">


<form:form method="post" modelAttribute="loginForm" action="j_spring_security_check" id="loginForm" cssClass="form-horizontal"
           autocomplete="off">
    <fieldset>
        <legend>Enter Your Information</legend>

        <c:set var="emailErrors"><form:errors path="email"/></c:set>
        <div class="control-group<c:if test="${not empty emailErrors}"> error</c:if>">
            <label class="control-label" for="field-email">Email</label>

            <div class="controls">
                <form:input path="email" id="field-email" tabindex="1" maxlength="45" placeholder="Email"/>
                <form:errors path="email" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <c:set var="passwordErrors"><form:errors path="password"/></c:set>
        <div class="control-group<c:if test="${not empty passwordErrors}"> error</c:if>">
            <label class="control-label" for="field-password">Password</label>
            <div class="controls">
                <form:input type="password" path="password" id="field-password" tabindex="2" maxlength="35" placeholder="*****"/>
                <form:errors path="password" cssClass="help-inline" element="span"/>
            </div>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Log in</button>
        </div>
    </fieldset>
    
    <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
    
</form:form>




	<c:if test="${page_error != null }">
        <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4>Error!</h4>
                ${page_error}
        </div>
    </c:if>

</div>
		</div>
		<div class="clear"></div>
		</div>

<c:import url="template/footer.jsp" />
