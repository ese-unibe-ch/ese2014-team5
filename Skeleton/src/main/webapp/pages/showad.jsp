<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  


<c:import url="template/header.jsp" />

<div class="booking_room">
			<h4>${ad.title}</h4>
			
		</div>
		<div class="reservation">

<fmt:formatDate value="${ad.fromDate}" var="dateFrom" pattern="MM/dd/yyyy" />
	<fmt:formatDate value="${ad.toDate}" var="dateTo" pattern="MM/dd/yyyy" />
<div class="fotorama">
 <c:forEach items="${ad.pictures}" var="pic">
		<img style="float:left;" class="gallery" src="<c:url value="img/${pic.url}"/>"/>
		<br />
	</c:forEach>
</div>
  
	<br />
  <ul style="clear:left;">
  <li>Room: ${ad.roomDesc }</li>
  <li>People: ${ad.peopleDesc }</li>
  <li>Number of People: ${ad.numberOfPeople} </li>
  <li>Size: ${ad.roomSize }m&sup2;</li>
  <li>Available from: ${dateFrom } to: ${dateTo } </li>
  <li>Address: ${ad.address.street }, ${ad.address.plz } ${ad.address.city }</li>
  </ul>
  
  <%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	  <c:if test="${ad.user.username!=currentUser.username && currentUser.username!=null && bookmarked!=1}" >
		  <form:form action="showad?value=${ad.id }" modelAttribute="bookmarkForm" id="bookmarkForm" method="post">
		  	<form:input path="username" type="hidden" id="field-username" value="${currentUser.username }" />
		  	<form:input path="adNumber" type="hidden" id="field-adNumber" value="${ad.id }"/>
		  	<button type="submit" value="Bookmark">Bookmark</button>
		  </form:form>
	  </c:if>

<br />

	<div width="100%">
		<iframe width="100%" height="350px" 
		src="http://maps.google.de/maps?hl=de&q=${ad.address.street }, ${ad.address.plz } ${ad.address.city }&ie=UTF8&t=&z=17&iwloc=B&output=embed" frameborder="0" scrolling="no" marginheight="0" marginwidth="0">
		</iframe>
	</div>
  
        </div>
		</div>
		<div class="clear"></div>
		</div>
                
        <div>	
	<button type="changeAdvert" onclick="location.href='edit-advert'" class="btn btn-primary">Edit advert</button>
	</div>



<c:import url="template/footer.jsp" />