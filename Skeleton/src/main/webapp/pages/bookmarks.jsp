<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>

<c:import url="template/header.jsp" />

<div class="booking_room">
	<h4>Bookmarks</h4>			
</div>

<div class="reservation">
	<c:if test="${empty adList}">
		You have no bookmarks yet.</br></br>
	</c:if>
	<c:if test="${not empty adList}">
		
		<c:forEach var="ad" items="${adList}">
			<div id="profilead_result">
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
				<form:form action="removeBookmark" method="post">
					<input type="hidden" name="username" value="<%=SecurityContextHolder.getContext().getAuthentication().getName()%>" />
					<button type="submit" name="adid" value="${ad.id }" >Remove</button>
				</form:form>
			</div>
		</c:forEach>
	</c:if>
</div>

</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />