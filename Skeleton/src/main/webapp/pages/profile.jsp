<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp" />
	
<div class="reservation">
<legend>${currentUser.firstName} ${currentUser.lastName}'s profile</legend>

	<img style="width: 250px;" class="pic" src="<c:url value="img/${currentUser.userData.picture.url}"/>"/>
	<br />
	<br />
		<p>Quote: ${currentUser.userData.quote}</p>
		<p>E-Mail: ${currentUser.email}</p>
		<p>Age: ${currentUser.userData.age}</p>
		<p>Biography: ${currentUser.userData.bio}</p>
		<p>Hobbies: ${currentUser.userData.hobbies}</p>
		<p>Profession: ${currentUser.userData.profession}</p>
		</br>
		<c:if test="${currentUser.email == loggedInUser.email}">
			<button type="submit" onclick="location.href='edit-profile'" class="btn btn-primary">Edit profile</button>
		</c:if>
	
</div>


</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />