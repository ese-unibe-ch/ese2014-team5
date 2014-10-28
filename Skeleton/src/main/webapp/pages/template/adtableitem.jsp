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