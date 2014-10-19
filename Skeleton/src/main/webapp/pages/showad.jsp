<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<h3>${ad.title}</h3>

Room: ${ad.roomDescription }
People: ${ad.peopleDescription }
Size: ${ad.size }
Address: ${ad.address.street }, ${ad.address.city } ${ad.address.plz }

<c:forEach items="${ad.pictures}" var="pic">
		<img src="<c:url value="${pic.url}"/>"/>
		<br />
	</c:forEach>


<c:import url="template/footer.jsp" />