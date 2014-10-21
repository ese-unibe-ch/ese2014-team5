<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  


<c:import url="template/header.jsp" />

<style type="text/css">
img.gallery {
	width: 150px;
}
</style>

<div class="booking_room">
			<h4>${ad.title}</h4>
			
		</div>
		<div class="reservation">

<fmt:formatDate value="${ad.fromDate}" var="dateFrom" pattern="MM/dd/yyyy" />
	<fmt:formatDate value="${ad.toDate}" var="dateTo" pattern="MM/dd/yyyy" />

  <c:forEach items="${ad.pictures}" var="pic">
		<img style="float:left;" class="gallery" src="<c:url value="img/${pic.url}"/>"/>
		<br />
	</c:forEach>
	<br />
  <ul style="clear:left;">
  <li>Room: ${ad.roomDesc }</li>
  <li>People: ${ad.peopleDesc }</li>
  <li>Size: ${ad.roomSize }m^2</li>
  <li>Available from: ${dateFrom } to: ${dateTo } </li>
  <li>Address: ${ad.address.street }, ${ad.address.plz } ${ad.address.city }</li>
  </ul>

<br />

	<div style="width: 300px">
		<iframe width="300" height="300" 
		src="http://maps.google.de/maps?hl=de&q=${ad.address.street }, ${ad.address.plz } ${ad.address.city }&ie=UTF8&t=&z=17&iwloc=B&output=embed" frameborder="0" scrolling="no" marginheight="0" marginwidth="0">
		</iframe>
	</div>
  
        </div>
		</div>
		<div class="clear"></div>
		</div>


<c:import url="template/footer.jsp" />