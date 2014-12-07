<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
<script type="text/javascript"  src="web/js/bootstrap-datepicker.js"></script>
<style>
.day {
cursor: pointer;}
</style>
<script type="text/javascript">

$(document).ready(function() {
	
	$("#lHome").parent().addClass("active");
	
	var i = 1;
	$(document).on("click", "result", function(){
		$(location).attr('href',"showad?value="+$(this).data("value"));
	});
	
	<c:if test="${hasResults==1}" >
	
		$('html,body').animate({scrollTop: $("#resultTable").offset().top + 300},'slow');

	</c:if>
	
	<c:if test="${maxPrize != null}">
	if(${maxPrice}==0)
		maxPrice = 3000;
	else
		maxPrice = ${maxPrice};
	if(${maxSize}==0)
		maxSize = 200;
	else
		maxSize = ${maxSize};
	</c:if>
	
	<c:if test="${maxPrize == null}">
	var maxPrice = 3000;
	var minPrice = 0;
	var maxSize = 200;
	var minSize = 0;
	</c:if>
	
	$( "#slider-range-price" ).slider({
	      range: true,
	      min: minPrice,
	      max: maxPrice,
	      values: [ minPrice, maxPrice ],
	      slide: function( event, ui ) {
	        $( "#field-fromPrice" ).val(ui.values[ 0 ]);
	        $( "#field-toPrice" ).val(ui.values[ 1 ]);
	        $( "#amountPrice" ).val(ui.values[ 0 ] + " - " + ui.values[ 1 ] );
	      }
	    });
	

	$( "#slider-range-size" ).slider({
	      range: true,
	      min: minSize,
	      max: maxSize,
	      values: [ minSize, maxSize ],
	      slide: function( event, ui ) {
	        $( "#field-fromSize" ).val(ui.values[ 0 ]);
	        $( "#field-toSize" ).val(ui.values[ 1 ]);
	        $( "#amountSize" ).val( ui.values[ 0 ] + " - " + ui.values[ 1 ]);
	      }
	    });
	
	$("#slider-range-price").slider('values',0,0);
	$("#slider-range-price").slider('values',1,maxPrice);
	$( "#amountPrice" ).val(minPrice + " - " + maxPrice  );
	$("#field-fromPrice").val(minPrice);
	$("#field-toPrice").val(maxPrice);
	
	$("#slider-range-size").slider('values',0,0);
	$("#slider-range-size").slider('values',1,maxSize);
	$( "#amountSize" ).val(	minSize + " - " + maxSize );
	$("#field-fromSize").val(minSize);
	$("#field-toSize").val(maxSize);
	
	
    $("#add").click(function(){
    	$("#files").append("<div class=\"secfile\"> File to upload: <input type=\"file\" name=\"files["+ (i++) +"]\"><input type=\"button\" class=\"delete\" value=\"Delete\"></div>");
    });
    
    $(document).on("click", ".delete", function() {
    	$(this).parent().remove();
    });
    
    $("input:file").change(function () {
    	$("#form2").submit();
    });
    
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
     
    
    
    $("#advanced_check").change(function(){

    	if(!$("#advanced_check").is(":checked"))
    	{	
    		$("#advanced").animate({height:'0px'},200);
    	}
    	else
    	{
    		$("#advanced").animate({height:'200px'},200);
    	}
    });
    
    $('#field-fromDate').datepicker({});
	$('#field-toDate').datepicker({});
    
    $('#field-fromDate').attr('readonly', true);
    $('#field-toDate').attr('readonly', true);
});
</script>


<c:if test="${page_error != null }">
	<div class="alert alert-error">
	    <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
	    <h4>Error!</h4>
	        ${page_error}
	</div>
</c:if>
<c:if test="${msg != null }">
	<div class="alert alert-success">
	    <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
<!-- 	    <h4>Success!</h4> -->
	        ${msg}
	</div>
</c:if>

<div class="reservation">
<legend>Find a wonderful room now!</legend>
	
	<form:form method="post" modelAttribute="searchForm" action="index" id="searchForm" cssClass="form-horizontal"
           autocomplete="off">
   		<fieldset>
   		
   			<div class="control-group">
	            <label class="control-label" for="field-search">Search</label>
	            <div class="controls">
	                <form:input path="search" id="field-search" tabindex="2" maxlength="35" placeholder="Search for specifics (near shops, art nouveau, etc.)"/>
	            </div>
	        </div>
            
            <div class="control-group">
	            <label class="control-label" for="amountPrice">Price (CHF)</label>
	            <div class="controls">
				 <input type="text" id="amountPrice" readonly style="border:1px solid #ccc; color:#f6931f; font-weight:bold;" value="${minPrice} - ${maxPrice}">
	            	<div id="slider-range-price"></div>
	            	<form:input type="hidden"  path="fromPrice" id="field-fromPrice" tabindex="2" maxlength="35" placeholder="CHF"/>
	            	<form:input type="hidden" path="toPrice" id="field-toPrice" tabindex="2" maxlength="35" placeholder="CHF"/>
	            </div>	 
            </div>
            
            <div class="control-group">
	            <label class="control-label" for="amountSize">Size (m&sup2;)</label>
	            <div class="controls">
					  <input type="text" id="amountSize" readonly style="border:1px solid #ccc; color:#f6931f; font-weight:bold;" value="${minSize} - ${maxSize}">
	            	<div id="slider-range-size"></div>
	            	<form:input type="hidden"  path="fromSize" id="field-fromSize" tabindex="2" maxlength="35" placeholder="CHF"/>
	            	<form:input type="hidden" path="toSize" id="field-toSize" tabindex="2" maxlength="35" placeholder="CHF"/>
	            </div>	   
            </div>
            
            <div class="control-group">
	            <label class="control-label" for="field-nearCity" style="clear:left;">Area</label>
	            <div class="controls">
	                <form:input path="nearCity" id="field-nearCity" tabindex="2" maxlength="35" placeholder="e.g. Bern"/>
	            </div>
            </div>

			<hr>
            <label class="control-label">Advanced Search</label>
            <div class="controls">
            	<input type="checkbox" id="advanced_check"/>
            </div><br />
            <div style="float:left;"></div>
            <div id="advanced" style="height: 0px; overflow:hidden;"></br>
            
	            <div class="control-group">
		            <label class="control-label" for="field-numberOfPeople">No. of People</label>
		            <div class="controls">
		                <form:input path="numberOfPeople" id="field-numberOfPeople" tabindex="2" maxlength="150" placeholder="e.g. 4"/>
		            </div>
		        </div>
	   			
	   			<div class="control-group">
		            <label class="control-label" for="field-fromDate">From</label>
		            <div class="controls">
		                <form:input path="fromDate" readonly="readonly" id="field-fromDate" class="span2" tabindex="2" maxlength="150" placeholder="e.g. 02/23/14"/>  
		            </div>
		        </div>
	
				<div class="control-group">
		            <label class="control-label" for="field-toDate">Till</label>
		            <div class="controls">
		                <form:input path="toDate" readonly="readonly" id="field-toDate" class="span2" tabindex="2" maxlength="150" placeholder="e.g. 02/23/14"/>
		            </div>
		        </div>

		 	</div>
            
            
            
            
            
            <div class="form-actions">
            	<button type="submit" name="action" value="blist" class="btn btn-primary">Show List</button>
            	<button type="submit" name="action" value="bmap" class="btn btn-primary">Show Map</button>
            	<sec:authorize access="hasRole('ROLE_USER')">
            		<c:if test="${hasResults ==1}">
            			<button type="submit" name="action" value="bsave" class="btn btn-primary">Save Search</button>
            		</c:if>
				</sec:authorize>
        	</div>
        </fieldset>
        <form:input type="hidden" path="userId" id="field-user" value="${currentUser.id}"/>
	</form:form>
    <div id="resultTable" class="resultblock">
		<c:choose>
	    <c:when test="${displayMap==0 && hasResults==1}">
	    <div id="results" style="width:100%">
			<c:forEach items="${searchResults}" var="ad">
		     
		    <div class="result" onclick="javascript:location.href='showad?value=${ad.id}'">
				<c:forEach items="${ad.pictures}" varStatus="loopCount" var="pic">
				<c:if test="${loopCount.count eq 1}">
					<div style="float:left;width:150px;"><img class="gallery" src="<c:url value="img/${pic.url}"/>"/></div>
				</c:if>
				</c:forEach>
				<div class="resultinfo" style="margin-left:10px;float:left">
					<b style="font-family:Arial;font-size:14pt;">${ad.title}</b>
					<br />
					Price: CHF ${ad.roomPrice}, Size: ${ad.roomSize}m&sup2;
					<br />
					${ad.address.street},  ${ad.address.plz} ${ad.address.city}
				</div>
			</div>
			    			
			</c:forEach>
			<c:if test="${empty searchResults }">
			No results found.
			</c:if>
	    </div>
	    </c:when>
		<c:when test="${displayMap==1 && hasResults==1}">
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
		       				    "Price: ${ad.roomPrice}CHF, Size: ${ad.roomSize}m&sup2; </div><a onclick=\"javascript:location.href='showad?value=${ad.id}'\">Show Ad</a>",
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
				    zoom: 12,
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
				
				var bounds = new google.maps.LatLngBounds ();

				for (var i = 0, LtLgLen = qmarkers.length; i < LtLgLen; i++) {
					bounds.extend (qmarkers[i]);
				}
				map.fitBounds (bounds);
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
						    content: event.content,
						    maxWidth: 300,
						    minHeight: 200
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
	    </c:when>
	    </c:choose>
	</div>
		
</div>

</div>
<div class="clear"></div>
</div>

<c:import url="template/footer.jsp" />