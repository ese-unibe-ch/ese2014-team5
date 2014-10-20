<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
 <link rel="stylesheet" type="text/css" href="css/datepicker.css"/>
<script type="text/javascript"  src="js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
var i = 1;

$(document).ready(function() {
    $("#add").click(function(){
    	$("#files").append("<div class=\"secfile\"> File to upload: <input type=\"file\" name=\"files["+ (i++) +"]\"><input type=\"button\" class=\"delete\" value=\"Delete\"></div>");
    });
    
    $(document).on("click", ".delete", function() {
    	$(this).parent().remove();
    });
    
    $("input:file").change(function () {
    	$("#form2").submit();
    });
    
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
     
    var checkin = $('#field-fromDate').datepicker({
      onRender: function(date) {
        return date.valueOf() < now.valueOf() ? 'disabled' : '';
      }
    }).on('changeDate', function(ev) {
      if (ev.date.valueOf() > checkout.date.valueOf()) {
        var newDate = new Date(ev.date)
        newDate.setDate(newDate.getDate() + 1);
      }
      checkin.hide();
      $('#field-toDate')[0].focus();
    }).data('datepicker');
    var checkout = $('#field-toDate').datepicker({
      onRender: function(date) {
        return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
      }
    }).on('changeDate', function(ev) {
      checkout.hide();
    }).data('datepicker');
});
</script>

<h1>Create a new ad</h1>

<form:form method="post" modelAttribute="adCreationForm" action="newad?${_csrf.parameterName}=${_csrf.token}" id="adCreationForm" cssClass="form-horizontal"
           autocomplete="off" enctype="multipart/form-data">
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
        
		<c:set var="roomDescErrors"><form:errors path="roomDesc"/></c:set>
        <div class="control-group<c:if test="${not empty roomDescErrors}"> error</c:if>">
            <label class="control-label" for="field-roomDesc">Room Description</label>
            <div class="controls">
                <form:textarea path="roomDesc" id="field-roomDesc" rows="6" width="350px" tabindex="2" maxlength="500" placeholder="Describe your room (bright/historic/...).."/>
                <form:errors path="roomDesc" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <c:set var="peopleDescErrors"><form:errors path="peopleDesc"/></c:set>
        <div class="control-group<c:if test="${not empty peopleDescErrors}"> error</c:if>">
            <label class="control-label" for="field-peopleDesc">People Description</label>
            <div class="controls">
                <form:textarea path="peopleDesc" id="field-peopleDesc" rows="6" width="350px" tabindex="2" maxlength="500" placeholder="Describe the people living in the appartment (age/profession/...).."/>
                <form:errors path="peopleDesc" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <c:set var="roomSizeErrors"><form:errors path="roomSize"/></c:set>
        <div class="control-group<c:if test="${not empty roomSizeErrors}"> error</c:if>">
            <label class="control-label" for="field-roomSize">Size</label>
            <div class="controls">
                <form:input path="roomSize" id="field-roomSize"  tabindex="2" maxlength="150" placeholder="e.g. 18"/> m^2
                <form:errors path="roomSize" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <legend>Availability</legend>
        <c:set var="fromDateErrors"><form:errors path="fromDate"/></c:set>
        <div class="control-group<c:if test="${not empty fromDateErrors}"> error</c:if>">
            <label class="control-label" for="field-fromDate">from</label>
            <div class="controls">
                <form:input path="fromDate" id="field-fromDate" class="span2" tabindex="2" maxlength="150" placeholder="e.g. 02.04.14"/>
                <form:errors path="fromDate" cssClass="help-inline" element="span"/>
            </div>
        </div>
        
        <c:set var="toDateErrors"><form:errors path="toDate"/></c:set>
        <div class="control-group<c:if test="${not empty toDateErrors}"> error</c:if>">
            <label class="control-label" for="field-toDate">till</label>
            <div class="controls">
                <form:input path="toDate" id="field-toDate" class="span2" tabindex="2" maxlength="150" placeholder="e.g. 02.04.14"/> or leave empty for undefined
                <form:errors path="toDate" cssClass="help-inline" element="span"/>
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
        
        <legend>Images</legend>

		<div id="files">File to upload: <input type="file" name="files[0]"><br /> </div>
         
         <input type="button" value="Add another file" id="add">
    
   <div id="result">${result}</div>
        
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Create</button>
            <button type="button" class="btn" onclick="javascript:location.href='/sample'">Cancel</button>
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
