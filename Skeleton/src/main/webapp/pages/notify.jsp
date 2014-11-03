<%@page import="org.sample.controller.service.SampleServiceImpl"%>
<%@page import="org.sample.controller.service.Servlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="notifications" value=""/> 

<%=SampleServiceImpl.getNotificationsForJSP() %>

