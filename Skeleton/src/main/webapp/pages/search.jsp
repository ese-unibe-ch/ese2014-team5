<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />

<script type="text/javascript">
function open()
{
	window.location.replace("/showad?value=1");
}
$(document).ready(function(){
	$(document).on("click", "result", function(){
		$(location).attr('href',"showad?value="+$(this).data("value"));
	});
});
</script>

<c:if test="${page_error != null }">
        <div class="alert alert-error">
            <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
            <h4>Error!</h4>
                ${page_error}
        </div>
    </c:if>


<div class="booking_room">
			<h4>Search results</h4>
			
		</div>
		<div class="reservation">
<form:form method="post" modelAttribute="searchForm" action="search" id="searchForm" cssClass="form-horizontal"
           autocomplete="off">
   				 <fieldset>
            <label class="control-label" for="field-search">Search</label>
            <div class="controls">
                <form:input path="search" id="field-search" tabindex="2" maxlength="35" placeholder="Search for specifics (near shops, party-friendly, etc.).."/>
            </div>
            
            <div class="controls-from">
            	<label class="control-label ctrl" for="field-fromPrice">from</label>
            	<div class="controls">
            	    <form:input  path="fromPrice" id="field-fromPrice" tabindex="2" maxlength="35" placeholder="CHF"/>
            	</div>
            </div>
        
        	<div class="controls-to">
        	<label class="control-label" for="field-toPrice">to</label>
            <div class="controls">
                <form:input path="toPrice" id="field-toPrice" tabindex="2" maxlength="35" placeholder="CHF"/>
            </div>
            </div>
            
            <div class="controls-from">
            	<label class="control-label" for="field-fromSize">from</label>
            	<div class="controls">
            	    <form:input class="from" path="fromSize" id="field-fromSize" tabindex="2" maxlength="35" placeholder="m^2"/>
            	</div>
        	</div>
        	
        	<div class="controls-to">
        	<label class="control-label" for="field-toSize">to</label>
            <div class="controls">
                <form:input path="toSize" id="field-toSize" tabindex="2" maxlength="35" placeholder="m^2"/>
            </div>
            </div>
            
            <label class="control-label" for="field-nearCity" style="clear:left;">Area</label>
            <div class="controls">
                <form:input path="nearCity" id="field-nearCity" tabindex="2" maxlength="35" placeholder="e.g. Bern"/>
            </div>
            
            
            <div class="controlbox">
            
                Include favorites<form:checkbox path="favorites" id="field-favorites"/>
            </div>
            
            <div class="form-actions">
            	<button type="submit" name="action" value="blist" class="btn btn-primary">Show List</button>
            	<button type="submit" name="action" value="bmap" class="btn btn-primary">Show Map</button>
        	</div>
        </fieldset>
            </form:form>
            <c:choose>
            <c:when test="${displayMap==0}">
            <div id="results" style="width:100%">
             <c:forEach items="${searchResults}" var="ad">
             
       			<div class="result" style="width:100%;height:150px;padding:10px;" onclick="javascript:location.href='showad?value=${ad.id}'">
	       			<c:forEach items="${ad.pictures}" varStatus="loopCount" var="pic">
	             		<c:if test="${loopCount.count eq 1}"><img class="gallery" src="<c:url value="img/${pic.url}"/>"/></c:if>
					</c:forEach>
					<div class="resultinfo">
						<b style="font-family:Arial;font-size:14pt;">${ad.title}</b>
						<br />
						${ad.roomDesc}
						<br />
						Price: ${ad.roomPrice}CHF, Size: ${ad.roomSize}m&sup2;
					</div>
				</div>
				</a>
			</c:forEach>
            </div>
            </c:when>
            <c:otherwise>
                <style>
	      #map-canvas {
	        height: 450px;
	        widht: 450px;
	        margin: 0px;
	        padding: 0px
	      }
	    </style>
	    
	    	<div id="map-canvas"></div>
	    	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
	    <script>
	    
	    var results = [<c:forEach items="${searchResults}" var="ad" varStatus="ads">{ content:
							<c:forEach items="${ad.pictures}" varStatus="loopCount" var="pic">
	       	             		<c:if test="${loopCount.count eq 1}">"<div id='contentwindow'><img class='gallery' src='<c:url value='img/${pic.url}'/>'/><br />" +</c:if>
	       					</c:forEach>
	       					"${ad.title}" +
	       				    "${ad.roomDesc}" +
	       				    "Price: ${ad.roomPrice}CHF, Size: ${ad.roomSize}m&sup2; </div>",
	       				address:
	       						"${ad.address.street} ${ad.address.city} ${ad.address.plz}" }
	                     <c:if test="${ ! ads.last}" >,  </c:if> 
	       			</c:forEach>
	                   ];
	    
	    var map;
		var geocoder;
		
		  
		    var gmarkers = []; 
	   
			  function initialize() {
				    geocoder = new google.maps.Geocoder();
				    var latlng = new google.maps.LatLng(46.9510827861504654, 7.4386324175389165);
				    var myOptions = {
				      zoom: 8,
				      center: latlng,
				      mapTypeId: google.maps.MapTypeId.ROADMAP
				    };
				    map = new google.maps.Map(document.getElementById("map-canvas"), myOptions);

				    var marker, i;
				    for (i = 0; i < results.length; i++) {  
				    	
				       codeAddress(results[i]);
				       
				      
				      
				      // save the info we need to use later for the side_bar
				      gmarkers.push(marker);
				    }
				    
				    google.maps.event.addListener(map, 'click', function() {
				          infowindow.close(map,marker);
				    });
				  }

				  function codeAddress(event) {
				    geocoder.geocode( { 'address': event.address}, function(results, status) {
				      if (status == google.maps.GeocoderStatus.OK) {
				        map.setCenter(results[0].geometry.location);
				        var marker = new google.maps.Marker({
				            map: map,
				            position: results[0].geometry.location
				            
				        });
				        
				        var infowindow = new google.maps.InfoWindow({
				            content: event.content
				        });
				        
				        google.maps.event.addListener(marker, 'click', function() {
					          infowindow.setContent(event.content); 
					          infowindow.open(map,marker);
					    });
				        
				       
				      } else {
				        //alert("Geocode was not successful for the following reason: " + status);
				        return null
				      }
				    });
				  }
			
				  
				  
				  google.maps.event.addDomListener(window, 'load', initialize);
			
			
	
	    </script>
	    	</c:otherwise>
	    	</c:choose>
		
</div>
		</div>
		<div class="clear"></div>
		</div>

<c:import url="template/footer.jsp" />