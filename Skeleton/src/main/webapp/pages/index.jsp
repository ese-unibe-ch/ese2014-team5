<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />



<!-- <h2 class="title" style="opacity: 1;"><i class="bg"></i>WG-Site</h4><br />
	<h4 class="subtitle">We find you a room!</h4>-->
<!--start main -->

<style type="text/css">

</style>

		<div class="booking_room">
			<h4>Find a room</h4>
			<p>Enter your wishes below and make your dream of a wonderful room come true!</p>
		</div>
		<div class="reservation">
			<form:form method="post" modelAttribute="searchForm" action="search" id="searchForm" cssClass="form-horizontal"
           autocomplete="off">
   				 <fieldset>
            <label class="control-label" for="field-search">Search</label>
            <div class="controls">
                <form:input path="search" id="field-search" tabindex="2" maxlength="35" placeholder="Search for specifics (near shops, party-friendly, etc.).."/>
            </div>
            
            <div class="controls-from">
            	<label class="control-label ctrl" for="field-fromPrice">from</label>
            	<div class="controls">
            	    <form:input  path="fromPrice" id="field-fromPrice" tabindex="2" maxlength="35" placeholder="CHF"/>
            	</div>
            </div>
        
        	<div class="controls-to">
        	<label class="control-label" for="field-toPrice">to</label>
            <div class="controls">
                <form:input path="toPrice" id="field-toPrice" tabindex="2" maxlength="35" placeholder="CHF"/>
            </div>
            </div>
            
            <div class="controls-from">
            	<label class="control-label" for="field-fromSize">from</label>
            	<div class="controls">
            	    <form:input class="from" path="fromSize" id="field-fromSize" tabindex="2" maxlength="35" placeholder="m^2"/>
            	</div>
        	</div>
        	
        	<div class="controls-to">
        	<label class="control-label" for="field-toSize">to</label>
            <div class="controls">
                <form:input path="toSize" id="field-toSize" tabindex="2" maxlength="35" placeholder="m^2"/>
            </div>
            </div>
            
            <label class="control-label" for="field-nearCity" style="clear:left;">Area</label>
            <div class="controls">
                <form:input path="nearCity" id="field-nearCity" tabindex="2" maxlength="35" placeholder="e.g. Bern"/>
            </div>
            
            
            <div class="controlbox">
            
                Include favorites<form:checkbox path="favorites" id="field-favorites"/>
            </div>
            
            <div class="form-actions">
            <button type="submit" class="btn btn-primary">Search</button>
        	</div>
        </fieldset>
            </form:form>
        </div>
		</div>
		<div class="clear"></div>
		</div>


<c:import url="template/footer.jsp" />
