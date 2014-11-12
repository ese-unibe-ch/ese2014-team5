<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<div class="reservation">
<legend>${currentUser.firstName} ${currentUser.lastName}'s profile</legend>
	<div>
		<p>E-Mail: ${currentUser.email}</p>
		</br>
		<button type="submit" onclick="location.href='edit-profile'" class="btn btn-primary">Edit profile</button>
	</div>
</div>

</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />