<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  


<c:import url="template/header.jsp" />

<style type="text/css">
img {
	width: 150px;
}
</style>
<div class="row">
<div class="col-md-4"></div>
  <div class="col-md-8"><h4>${ad.title}</h4></div>
</div>
<div class="row">
  <div class="col-md-4">
  <c:forEach items="${ad.pictures}" var="pic">
		<img src="<c:url value="img/${pic.url}"/>"/>
		<br />
	</c:forEach>
  
  </div>
  <div class="col-md-8">Room: ${ad.roomDesc }</div>
  <div class="col-md-8">People: ${ad.peopleDesc }</div>
  <div class="col-md-8">Size: ${ad.roomSize }m^2</div>
  <fmt:formatDate value="${ad.fromDate}" var="dateFrom" pattern="MM/dd/yyyy" />
	<fmt:formatDate value="${ad.toDate}" var="dateTo" pattern="MM/dd/yyyy" />
  <div class="col-md-8">Available from: ${dateFrom } to: ${dateTo } </div>
  <div class="col-md-8">Address: ${ad.address.street }, ${ad.address.plz } ${ad.address.city }</div>
</div>
<br />
<div class="row">
  <div class="col-md-4">
	<div style="width: 300px">
		<iframe width="300" height="300" 
		src="http://maps.google.de/maps?hl=de&q=${ad.address.street }, ${ad.address.plz } ${ad.address.city }&ie=UTF8&t=&z=17&iwloc=B&output=embed" frameborder="0" scrolling="no" marginheight="0" marginwidth="0">
		</iframe>
	</div>
  </div>
 </div>




<c:import url="template/footer.jsp" />