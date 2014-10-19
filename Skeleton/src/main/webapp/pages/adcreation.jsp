<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />


<h1>Create a new ad</h1>

<form:form method="post" modelAttribute="adcreationForm" action="createAd" id="adcreationForm" cssClass="form-horizontal"
           autocomplete="off">
    <fieldset>
        <legend>General Info</legend>
        
        <c:set var="titleErrors"><form:errors path="title"/></c:set>
        <div class="control-group<c:if test="${not empty titleErrors}"> error</c:if>">
            <label class="control-label" for="field-title">Title</label>
            <div class="controls">
                <form:input path="title" id="field-title" tabindex="2" maxlength="150" width="350px" placeholder="Enter a title"/>
                <form:errors path="title" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
		<c:set var="roomDescriptionErrors"><form:errors path="roomDescription"/></c:set>
        <div class="control-group<c:if test="${not empty roomDescriptionErrors}"> error</c:if>">
            <label class="control-label" for="field-roomDescription">Room Description</label>
            <div class="controls">
                <form:textarea path="roomDescription" id="field-roomDescription" rows="6" width="350px" tabindex="2" maxlength="500" placeholder="Describe your room (bright/historic/...).."/>
                <form:errors path="roomDescription" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <c:set var="peopleDescriptionErrors"><form:errors path="peopleDescription"/></c:set>
        <div class="control-group<c:if test="${not empty peopleDescriptionErrors}"> error</c:if>">
            <label class="control-label" for="field-peopleDescription">People Description</label>
            <div class="controls">
                <form:textarea path="peopleDescription" id="field-peopleDescription" rows="6" width="350px" tabindex="2" maxlength="500" placeholder="Describe the people living in the appartment (age/profession/...).."/>
                <form:errors path="peopleDescription" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <c:set var="sizeErrors"><form:errors path="size"/></c:set>
        <div class="control-group<c:if test="${not empty sizeErrors}"> error</c:if>">
            <label class="control-label" for="field-size">Size</label>
            <div class="controls">
                <form:input path="size" id="field-size" tabindex="2" maxlength="150" placeholder="e.g. 18"/> m^2
                <form:errors path="size" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <legend>Availability</legend>
        <c:set var="fromErrors"><form:errors path="from"/></c:set>
        <div class="control-group<c:if test="${not empty fromErrors}"> error</c:if>">
            <label class="control-label" for="field-from">from</label>
            <div class="controls">
                <form:input path="from" id="field-from" tabindex="2" maxlength="150" placeholder="e.g. 02.04.14"/>
                <form:errors path="from" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <c:set var="toErrors"><form:errors path="to"/></c:set>
        <div class="control-group<c:if test="${not empty toErrors}"> error</c:if>">
            <label class="control-label" for="field-to">till</label>
            <div class="controls">
                <form:input path="to" id="field-to" tabindex="2" maxlength="150" placeholder="e.g. 02.04.14"/>
                <form:errors path="to" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <legend>Address</legend>
        <c:set var="streetErrors"><form:errors path="street"/></c:set>
        <div class="control-group<c:if test="${not empty streetErrors}"> error</c:if>">
            <label class="control-label" for="field-street">Street</label>
            <div class="controls">
                <form:input path="street" id="field-street" tabindex="2" maxlength="150" placeholder="Downing Street 10"/>
                <form:errors path="street" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <c:set var="cityErrors"><form:errors path="city"/></c:set>
        <div class="control-group<c:if test="${not empty cityErrors}"> error</c:if>">
            <label class="control-label" for="field-city">City</label>
            <div class="controls">
                <form:input path="city" id="field-city" tabindex="2" maxlength="150" placeholder="Bern"/>
                <form:errors path="city" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <c:set var="plzErrors"><form:errors path="plz"/></c:set>
        <div class="control-group<c:if test="${not empty plzErrors}"> error</c:if>">
            <label class="control-label" for="field-plz">Postcode</label>
            <div class="controls">
                <form:input path="plz" id="field-plz" tabindex="2" maxlength="150" placeholder="3000"/>
                <form:errors path="plz" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Create</button>
            <button type="button" class="btn">Cancel</button>
        </div>
    </fieldset>
</form:form>




	<c:if test="${page_error != null }">
        <div class="alert alert-error">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <h4>Error!</h4>
                ${page_error}
        </div>
    </c:if>


<c:import url="template/footer.jsp" />
