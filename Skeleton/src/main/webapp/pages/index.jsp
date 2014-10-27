<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<script type="text/javascript">
function open()
{
	window.location.replace("/showad?value=1");
}
$(document).ready(function(){
	
	$("#slider-range-price").slider('values',0,${minPrice}); // sets first handle (index 0) to 50
	$("#slider-range-price").slider('values',1,${maxPrice}); // sets second handle (index 1) to 80
	$( "#amountPrice" ).val( 	"CHF ${minPrice} - CHF ${maxPrice}"  );
	
	$("#slider-range-size").slider('values',0,${minSize}); // sets first handle (index 0) to 50
	$("#slider-range-size").slider('values',1,${maxSize}); // sets second handle (index 1) to 80
	$( "#amountSize" ).val(	"${minSize}m^2 - ${maxSize}m^2" );
});
</script>

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
            
            <label class="control-label" for="amountPrice">Price</label>
            <div class="controls">
			 <input type="text" id="amountPrice" readonly style="border:0; color:#f6931f; font-weight:bold;">
            	<div id="slider-range-price"></div>
            	<form:input type="hidden"  path="fromPrice" id="field-fromPrice" tabindex="2" maxlength="35" placeholder="CHF"/>
            	<form:input type="hidden" path="toPrice" id="field-toPrice" tabindex="2" maxlength="35" placeholder="CHF"/>
            </div>	 
            
            <label class="control-label" for="amountSize">Size</label>
            <div class="controls">
				  <input type="text" id="amountSize" readonly style="border:0; color:#f6931f; font-weight:bold;">
            	<div id="slider-range-size"></div>
            	<form:input type="hidden"  path="fromSize" id="field-fromSize" tabindex="2" maxlength="35" placeholder="CHF"/>
            	<form:input type="hidden" path="toSize" id="field-toSize" tabindex="2" maxlength="35" placeholder="CHF"/>
            </div>	   
            
            <label class="control-label" for="field-nearCity" style="clear:left;">Area</label>
            <div class="controls">
                <form:input path="nearCity" id="field-nearCity" tabindex="2" maxlength="35" placeholder="e.g. Bern"/>
            </div>
            
            
            <div class="controlbox">
            
                Include favorites<form:checkbox path="favorites" id="field-favorites"/>
            </div>
            
            <div class="form-actions">
            	<button type="submit" name="action" value="blist" class="btn btn-primary">Show List</button>
            	<button type="submit" name="action" value="bmap" class="btn btn-primary">Show Map</button>
        	</div>
        </fieldset>
            </form:form>
        </div>
		</div>
		<div class="clear"></div>
		</div>


<c:import url="template/footer.jsp" />
