<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  


<c:import url="template/header.jsp" />
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
<script type="text/javascript">

$(document).ready(function(){

	$("#lBook").parent().addClass("active");
	$("#enquiryFormDiv").hide();
	$("#showEnquiryForm").click(function(){
		$("#enquiryFormDiv").show();
        $(this).hide();

	});
	
});

var map;
var geocoder;
function initialize() {
	geocoder = new google.maps.Geocoder();
 var mapOptions = {
		  
   zoom: 8,
   center: new google.maps.LatLng(46.9479222,7.4446085,7)
 };
 map = new google.maps.Map(document.getElementById('map-canvas'),
     mapOptions);
 codeAddress();
}

function codeAddress() {
   var address = "${ad.address.street } ${ad.address.plz } ${ad.address.city }";
   geocoder.geocode( { 'address': address}, function(results, status) {
     if (status == google.maps.GeocoderStatus.OK) {
       map.setCenter(results[0].geometry.location);
       var marker = new google.maps.Marker({
           map: map,
           position: results[0].geometry.location
       });
       map.setCenter(results[0].geometry.location);
       map.setZoom(16);
     } else {
       
     }
   });
 }

google.maps.event.addDomListener(window, 'load', initialize);


</script>
<div class="reservation">
<c:if test="${msg != null }">
	<div class="alert alert-success">
	    <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
<!-- 	    <h4>Success!</h4> -->
	        ${msg}
	</div>
</c:if>
<c:if test="${page_error != null }">
	<div class="alert alert-error">
	    <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
	    <h4>Error!</h4>
	        ${page_error}
	</div>
</c:if>

	<legend>${ad.title}</legend>


    <fmt:formatDate value="${ad.fromDate}" var="dateFrom" pattern="dd.MM.yy" />
    <fmt:formatDate value="${ad.toDate}" var="dateTo" pattern="dd.MM.yy" />
    <div class="fotorama">
        <c:forEach items="${ad.pictures}" var="pic">
            <img style="float:left;margin-bottom:20px;max-width:500px;height:auto;" class="gallery" src="<c:url value="img/${pic.url}"/>"/>
            <br />
        </c:forEach>
    </div>

    <br />
    
    <style>
	      #map-canvas {
	        height: 300px;
	        width: 300px;
	        display:block;
	      }
	      
	     .content_ul > li {
	     	padding-bottom: 0.5em;
	     	width: 100%;
	     } 
	</style>
	<div style="margin-bottom:20px;">
	    <ul style="text-indent:0;margin:0px;padding:0px;" class="content_ul">
	        <li><span style="font-weight:bold;">Room: </span>${ad.roomDesc }</li>
	        <li><span style="font-weight:bold;">People: </span>${ad.peopleDesc }</li>
	        <li><span style="font-weight:bold;">Number of People: </span>${ad.numberOfPeople} </li>
	        <li><span style="font-weight:bold;">Size: </span>${ad.roomSize }m&sup2;</li>
	        <li><span style="font-weight:bold;">Available from: </span>${dateFrom }<span style="font-weight:bold;"> to: </span>${dateTo } </li>
	        <li><span style="font-weight:bold;">Address: </span>${ad.address.street }, ${ad.address.plz } ${ad.address.city }</li>
			<li><span style="font-weight:bold;">Roommate(s): </span><a href="profile?name=${ad.user.username }">${ad.user.firstName } ${ad.user.lastName }</a></li>
	    </ul>
    </div>
    
    <div style="text-align:center;">
    	<div id="map-canvas"></div>
	</div>

    <%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <c:if test="${ad.user.username!=currentUser.username && currentUser.username!=null && bookmarked!=1}" >
        <form:form action="showad?value=${ad.id }" modelAttribute="bookmarkForm" id="bookmarkForm" method="post">
            <form:input path="username" type="hidden" id="field-username" value="${currentUser.username }" />
            <form:input path="adNumber" type="hidden" id="field-adNumber" value="${ad.id }"/>
            <button type="submit" class="btn btn-primary" value="Bookmark">Bookmark</button> 
        </form:form>
    </c:if>
    </br>
    <c:if test="${ad.user.username!=currentUser.username && currentUser.username!=null && sentenquiry!=1}" >
	  <button class="btn btn-primary" id="showEnquiryForm">Send Enquiry</button>
	  <div id="enquiryFormDiv" style="width:94%;">
		<form:form action="sendenquiry" id="enquiryForm" method="post">
		  	<textarea name="enquirytext" placeholder="e.g. I'd like to visit your room" rows="6" width="350px" style="resize:vertical;" tabindex="2" maxlength="500"/></textarea>
		  	<input type="hidden" name="adid" value="${ad.id }" />
		  	<button type="submit" class="btn btn-primary" >Submit</button>
		  </form:form>
	  </div>
	  </c:if>
    <c:if test="${ad.user.username==currentUser.username}">
    	</br>
    	<button type="submit" onclick="location.href = 'addediting?value=${ad.id}'" class="btn btn-primary">Edit add</button>
	</c:if>
        

	</div>
		</div>
		<div class="clear"></div>
		</div>
	

     <c:import url="template/footer.jsp" />