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
		
		<c:forEach var="ad" items="${adList}">
			<div id="profilead_result">
				<div class="result" style="width:100%;height:100px;padding:10px;">
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
						Enquiries: 5<br/>
						<span style = "color:green;">New: 2</span><br/><br/>
						<button type="button" onclick="location.href='showad?value=${ad.id}'" class="btn btn-primary">Preview</button>
						<button type="button" onclick="location.href='showenquiries?value=${ad.id}'" class="btn btn-primary">Show enquiries</button>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
</div>

</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />