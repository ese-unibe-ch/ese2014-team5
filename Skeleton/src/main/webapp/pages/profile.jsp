<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<div class="booking_room">
			<h4>Profile</h4>
			
		</div>
		<div class="reservation">
<h3>${currentUser.firstName} ${currentUser.lastName}'s profile</h3>
<div>
<p>E-Mail: ${currentUser.email}</p>
</div>
		</div>
		</div>
		<div class="clear"></div>
		</div>


<c:import url="template/footer.jsp" />