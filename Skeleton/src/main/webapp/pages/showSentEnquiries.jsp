<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
	
<div class="reservation">
<legend>My sent enquiries</legend>

<ul id="enquiries">
	<c:forEach items="${enquiriesList}" var="enq">
		<li> <a href="showad?value=${enq.advert.id}">${enq.advert.title}</a>  </li>
	</c:forEach>
</ul>
	
</div>


</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />