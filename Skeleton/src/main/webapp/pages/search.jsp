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
	$(document).on("click", "result", function(){
		$(location).attr('href',"showad?value="+$(this).data("value"));
	});
});
</script>

<c:if test="${page_error != null }">
        <div class="alert alert-error">
            <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
            <h4>Error!</h4>
                ${page_error}
        </div>
    </c:if>


<div class="booking_room">
			<h4>Search results</h4>
			
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
            
            <div id="results" style="width:100%">
             <c:forEach items="${searchResults}" var="ad">
             
       			<div class="result" style="width:100%;height:150px;padding:10px;" onclick="javascript:location.href='showad?value=${ad.id}'">
	       			<c:forEach items="${ad.pictures}" varStatus="loopCount" var="pic">
	             		<c:if test="${loopCount.count eq 1}"><img class="gallery" src="<c:url value="img/${pic.url}"/>"/></c:if>
					</c:forEach>
					<div class="resultinfo">
						<b style="font-family:Arial;font-size:14pt;">${ad.title}</b>
						<br />
						${ad.roomDesc}
						<br />
						Price: ${ad.roomPrice}CHF, Size: ${ad.roomSize}m&sup2;
					</div>
				</div>
				</a>
			</c:forEach>
            </div>
</div>
		</div>
		<div class="clear"></div>
		</div>

<c:import url="template/footer.jsp" />