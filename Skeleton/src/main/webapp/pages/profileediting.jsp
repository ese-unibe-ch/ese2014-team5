<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<div class="reservation">
<script>
$(document).ready(function(){
	$(".lProf").parent().addClass("active");
});
</script>
<legend>Edit your profile</legend>
	<form:form method="post" modelAttribute="profileUpdateForm" action="updateAccount?${_csrf.parameterName}=${_csrf.token}" id="profileUpdateForm" cssClass="form-horizontal"
	           autocomplete="off" enctype="multipart/form-data">
	    <fieldset>
	
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
	       
	        <div class="control-group">
	            <label class="control-label" for="field-bio">Biography</label>
	            <div class="controls">
	                <textarea name="bio" id="field-bio" rows="6" width="350px" style="resize:vertical;" tabindex="2" maxlength="500" placeholder="Describe who you are">${currentUser.userData.bio}</textarea>
	                <form:errors path="bio" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	        
	        <div class="control-group">
	            <label class="control-label" for="field-hobbies">Hobbies</label>
	            <div class="controls">
	                <form:input path="hobbies" id="field-hobbies" tabindex="1" maxlength="45" value="${currentUser.userData.hobbies}"/>
	                <form:errors path="hobbies" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	        
	        <div class="control-group">
	            <label class="control-label" for="field-age">Age</label>
	            <div class="controls">
	                <form:input type="number" path="age" id="field-age" tabindex="1" maxlength="45" value="${currentUser.userData.age}"/>
	                <form:errors path="age" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	        
	        <div class="control-group">
	            <label class="control-label" for="field-profession">Profession</label>
	            <div class="controls">
	                <form:input path="profession" id="field-profession" tabindex="1" maxlength="45" value="${currentUser.userData.profession}"/>
	                <form:errors path="profession" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	        
	        <div class="control-group">
	            <label class="control-label" for="field-quote">Personal Quote</label>
	            <div class="controls">
	                <form:input path="quote" id="field-quote" tabindex="1" maxlength="45" value="${currentUser.userData.quote}"/>
	                <form:errors path="quote" cssClass="help-inline" element="span"/>
	            </div>
	        </div>
	        
	        <div id="file">File to upload: <input type="file" name="file"><br /> </div>
	
	        <div class="form-actions">
	            <button type="submit" class="btn btn-primary">Confirm</button>
	            <button type="button" onclick="location.href='profile?name=${currentUser.username}'" class="btn">Cancel</button>
	        </div>
	
	
	    </fieldset>
	</form:form>
</div>

</div>
<div class="clear"></div>
</div>

<c:import url="template/footer.jsp" />