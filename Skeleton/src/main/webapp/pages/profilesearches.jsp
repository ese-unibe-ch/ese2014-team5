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
			<ul>
				<c:forEach var="listValue" items="${searchList}">
					<li>${listValue}</li>
				</c:forEach>
			</ul>
	</c:if>
</div>

</div>
<div class="clear"></div>
</div>

<c:import url="template/footer.jsp" />