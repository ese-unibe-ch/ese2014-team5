<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<div class="reservation">
<legend>Edit your profile</legend>
	<form:form method="post" modelAttribute="profileUpdateForm" action="updateAccount" id="profileUpdateForm" cssClass="form-horizontal"
	           autocomplete="off">
	    <fieldset>
	        <legend>Make your changes, then confirm</legend>
	
	        <c:set var="FirstNameErrors"><form:errors path="FirstName"/></c:set>
	        <div class="control-group<c:if test="${not empty firstNameErrors}"> error</c:if>">
	            <label class="control-label" for="field-FirstName">First Name</label>
	            <div class="controls">
	                <form:input path="FirstName" id="field-FirstName" tabindex="2" maxlength="35" value="${currentUser.firstName}"/>
	                <form:errors path="FirstName" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	            
	        <c:set var="LastNameErrors"><form:errors path="LastName"/></c:set>
	        <div class="control-group<c:if test="${not empty lastNameErrors}"> error</c:if>">
	            <label class="control-label" for="field-LastName">Last Name</label>
	            <div class="controls">
	                <form:input path="LastName" id="field-LastName" tabindex="3" maxlength="35" value="${currentUser.lastName}"/>
	                <form:errors path="LastName" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	
	        <c:set var="EmailErrors"><form:errors path="Email"/></c:set>
	        <div class="control-group<c:if test="${not empty emailErrors}"> error</c:if>">
	            <label class="control-label" for="field-Email">Email</label>
	
	            <div class="controls">
	                <form:input path="Email" id="field-Email" tabindex="1" maxlength="45" value="${currentUser.email}"/>
	                <form:errors path="Email" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	
	        
	        <c:set var="passwordErrors"><form:errors path="password"/></c:set>
	        <div class="control-group<c:if test="${not empty passwordErrors}"> error</c:if>">
	            <label class="control-label" for="field-password">Password</label>
	            <div class="controls">
	                <form:input type="password" path="password" id="field-password" tabindex="3" maxlength="35"/>
	                <form:errors path="password" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	        
	                <c:set var="passwordRepeatErrors"><form:errors path="passwordRepeat"/></c:set>
	        <div class="control-group<c:if test="${not empty passwordRepeatErrors}"> error</c:if>">
	            <label class="control-label" for="field-passwordRepeat">Repeat Password</label>
	            <div class="controls">
	                <form:input type="password" path="passwordRepeat" id="field-passwordRepeat" tabindex="3" maxlength="35"/>
	                <form:errors path="passwordRepeat" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	
	        <div class="form-actions">
	            <button type="submit" class="btn btn-primary">Confirm</button>
	            <button type="button" onclick="location.href='profile?id=${currentUser.id}'" class="btn">Cancel</button>
	        </div>
	
	
	    </fieldset>
	</form:form>
</div>

</div>
<div class="clear"></div>
</div>

<c:import url="template/footer.jsp" />