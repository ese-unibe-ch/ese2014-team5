<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<div class="reservation">
<legend>My Ads</legend>
	<c:if test="${empty adList}">
		You haven't placed an advert yet.</br></br>
		<button type="submit" onclick="location.href='adcreation'" class="btn btn-primary">Create an add</button>
	</c:if>
	<c:if test="${not empty adList}">

		<div class="resultblock" id="profilead_result">
			<div style="width:101%;padding:0px 1% 20px 2%;text-align:right;display:inline-block;">
				<button type="submit" onclick="location.href='adcreation'" class="btn btn-primary" style="float:right;">Create an add</button>
			</div>
			<c:forEach var="ad" items="${adList}">
				<div class="result" onclick="location.href='showad?value=${ad.id}'">
	       			<c:forEach items="${ad.pictures}" varStatus="loopCount" var="pic">
	             		<c:if test="${loopCount.count eq 1}"><div style="float:left;"><img class="gallery" src="<c:url value="img/${pic.url}"/>"/></div></c:if>
					</c:forEach>
					<div class="resultinfo" style="margin-left:10px;margin-right:30px;float:left;">
						<p>
							<b style="font-family:Arial;font-size:14pt;">${ad.title}</b>
							<br />
							${ad.address.street}, ${ad.address.plz} ${ad.address.city}<br/>
							Price: ${ad.roomPrice}CHF, Size: ${ad.roomSize}m&sup2;
						</p>
					</div>
					<div class="resultinfo" style="margin-left:10px;float:right;text-align: right;">
						
						<span id="enqid${ad.id }"></span>
						<script type="text/javascript"> 
							$("#enqid${ad.id }").load("getnumenquiriesforad.htm?id=${ad.id }"); 
							function setNotesReadForAdAndShowEnquiries(id)
							{
								$.get( "setreadenquiries?id=" + id, function() {
									
								});
								window.location.href='showenquiries?value=${ad.id}';
								
								var e = window.event;
								e.cancelBubble = true;
								if (e.stopPropagation) e.stopPropagation();
							}
						</script>
						<div style="float:right;position:absolute;bottom:10px;right:10px;">
							<button type="button" name="enquiries" onclick="setNotesReadForAdAndShowEnquiries(${ad.id})" class="btn btn-primary">Show enquiries</button>
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