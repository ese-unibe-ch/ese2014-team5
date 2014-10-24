<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:import url="template/header.jsp" />

<div class="booking_room">
			<h4>Login</h4>
			
		</div>
		<div class="reservation">

<form:form class="login-form" action="j_spring_security_check" method="POST" >
		<fieldset>
			<legend>Login Here</legend>
			
			<p> 
			<label for="j_username">Username</label>:
			<input id="j_username" name="j_username" size="20" maxlength="50" type="text"/>
			</p>
			
			<p>
			<label for="j_password">Password</label>:
			<input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
			</p>
			
			<p><input type="submit" value="Login"/></p>
		</fieldset>
	</form:form>
	
	</div>
		</div>
		<div class="clear"></div>
		</div>
	
	
	<c:import url="template/footer.jsp" />