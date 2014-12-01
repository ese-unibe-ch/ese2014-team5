<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:import url="template/header.jsp" />
	
<div class="reservation">
<script>
$(document).ready(function(){
	$("#lProf").parent().addClass("active");
});
</script>
<legend>${currentUser.firstName} ${currentUser.lastName}'s profile</legend>

	<div style="width:100%;">
		<div style="float:left;">
			<img style="padding:5px;border: solid #e5e5e5 1px;" class="pic" src="<c:url value="img/${currentUser.userData.picture.url}"/>"/>
		</div>
		<div style="padding-left:30px;float:left;">
			</br>
			<span style="font-weight:bold;font-size:18pt;font-family:Comic Sans MS,sans-serif;">
				<c:if test="${currentUser.userData.quote}">"${currentUser.userData.quote}"</c:if>
			</span>
		</div>
	</div>
	
	<div style="clear:both;padding-top:20px;">
		<div style="float:left;">
			<p><span style="font-weight:bold;">E-Mail: </span></p>
			<p><span style="font-weight:bold;">Age: </span></p>
			<p><span style="font-weight:bold;">Biography: </span></p>
			<p><span style="font-weight:bold;">Hobbies: </span></p>
			<p><span style="font-weight:bold;">Profession: </br></span></p>
		</div>
		<div style="padding-left:14px;float:left;">
			<p>${currentUser.email}</br></p>
			<p>${currentUser.userData.age}</br></p>
			<p>${currentUser.userData.bio}</br></p>
			<p>${currentUser.userData.hobbies}</br></p>
			<p>${currentUser.userData.profession}</br></br></p>
		</div>
	</div>
	<div class="form-actions" style="clear:both;padding-top:20px;">
		<c:if test="${currentUser.email == loggedInUser.email}">
			<button type="submit" onclick="location.href='edit-profile'" class="btn btn-primary">Edit profile</button>
		</c:if>
	</div>
</div>


</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />