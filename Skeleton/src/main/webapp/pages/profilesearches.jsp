<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:import url="template/header.jsp" />

<c:if test="${msg != null }">
	<div class="alert alert-success">
	    <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
	        ${msg}
	</div>
</c:if>

<div class="reservation">
<legend>My saved searches</legend>
	<c:if test="${empty searchList}">
		You don't have any saved searches.
	</c:if>
	<c:if test="${not empty searchList}">
		<div class="resultblock" id="searchlist">
			<div style="padding:2%;padding-bottom:20px;">
				<div class="radio">
					<label class="radio-inline"><input type="radio" name="notifySearch" onclick="location.href='saved-searches?filter=0'" <c:if test="${empty selectedSearch}">checked</c:if> >Don't notify me if a new advert matches any of my saved searches.</label>
				</div>
			</div>
			
			<c:forEach items="${searchList}" var="listValue">
		    	<div id="profilesearches_result">
				    <div class="result" onclick="setreadSearch(${listValue.id},'index?action=blist&searchid=${listValue.id}')">
				    	<div style="width:50px;float:left;display:inline-block;">
				    		<input type="radio" name="notifySearch" id="${listValue.id}" onclick="javascript:location.href='saved-searches?filter=${listValue.id}';var e = window.event;e.cancelBubble = true;if (e.stopPropagation) e.stopPropagation();" <c:if test="${selectedSearch == listValue.id}">checked</c:if>>
				    	</div>
				    	<div style="float:left;">Search for:</div>
						<div class="resultinfo" style="margin-left:50px; float:left;display:inline-block;">
							<b style="font-family:Arial;font-size:14pt;">${listValue.freetext}</b>
							<br />
							Price: ${listValue.priceFrom} to ${listValue.priceTo} CHF, Size: ${listValue.sizeFrom} to ${listValue.sizeTo} m&sup2;
							<br />
							Amount of people: ${listValue.peopleAmount}, Area: ${listValue.area}
							<br />
							From <fmt:formatDate value="${listValue.fromDate}"/> to <fmt:formatDate value="${listValue.toDate}"/>
						</div>
						<div style="float:right;position:absolute;bottom:10px;right:10px;">
							<button type="submit" name="remove" onclick="javascript:location.href='saved-searches?value=${listValue.id}';var e = window.event;e.cancelBubble = true;if (e.stopPropagation) e.stopPropagation();" class="btn btn-primary">Remove</button>
						</div>
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