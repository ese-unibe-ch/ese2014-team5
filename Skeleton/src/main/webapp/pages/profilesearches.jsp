<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<div class="booking_room">
	<h3>Your saved searches</h3>
</div>

<div class="reservation">

	<c:if test="${empty searchList}">
		You don't have any saved searches.
	</c:if>
	<c:if test="${not empty searchList}">
		
		<div id="searchlist" style="width:100%">
			<c:forEach items="${searchList}" var="listValue">
		    
		    	<div id="profilesearches_result">
				    <div class="result" style="width:100%;height:100px;padding:10px;" onclick="#">
				    	<div style="float:left;">Search for:</div>
						<div class="resultinfo" style="float:left;">
							<b style="font-family:Arial;font-size:14pt;">${listValue.freetext}</b>
							<br />
							Price: ${listValue.priceFrom} to ${listValue.priceTo} CHF
							<br />
							Size: ${listValue.sizeFrom} to ${listValue.sizeTo} m&sup2;
							<br />
							Area: ${listValue.area}
						</div>
						<div style="float:right;padding-bottom:20px"><button type="submit" name="action" value="deletesearch" onclick="javascript:location.href='saved-searches?value=${listValue.id}'" class="btn btn-primary">Remove</button></div>
					</div>
				</div>
			    			
			</c:forEach>
    	</div>
	</c:if>
</div>

</div>
<div class="clear"></div>
</div>

<c:import url="template/footer.jsp" />