<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />


<h3>${currentUser.firstName} ${currentUser.lastName}'s profile</h3>
<div>
<p>E-Mail: ${currentUser.email}</p>
</div>

Go back to the <a href="register">Register</a>


<c:import url="template/footer.jsp" />