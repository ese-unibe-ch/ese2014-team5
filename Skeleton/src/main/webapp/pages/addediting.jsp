<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
<link rel="stylesheet" type="text/css" href="css/datepicker.css"/>
<script type="text/javascript"  src="js/bootstrap-datepicker.js"></script>
<script type="text/javascript">

    var i = 1;

    $(document).ready(function () {
    	$("#lEnq").parent().addClass("active");
        $("#add").click(function () {
            $("#files").append("<div class=\"secfile\"> File to upload: <input type=\"file\" name=\"files[" + (i++) + "]\"><button class=\"delete btn\" value=\"Delete\">Delete</button></div>");
        });

        $(document.body).on("click", ".delete", function () {
            $(this).parent().remove();
        });

        $("input:file").change(function () {
            $("#form2").submit();
        });

        var nowTemp = new Date();
        var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

        var checkin = $('#field-fromDate').datepicker({
            onRender: function (date) {
                return date.valueOf() < now.valueOf() ? 'disabled' : '';
            }
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf() > checkout.date.valueOf()) {
                var newDate = new Date(ev.date)
                newDate.setDate(newDate.getDate() + 1);
            }
            checkin.hide();
            $('#field-toDate')[0].focus();
        }).data('datepicker');
        var checkout = $('#field-toDate').datepicker({
            onRender: function (date) {
                return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
            }
        }).on('changeDate', function (ev) {
            checkout.hide();
        }).data('datepicker');
        
        $('#field-fromDate').attr('readonly', true);
        $('#field-toDate').attr('readonly', true);
    });
    
    function deleteAd(id)
    {
    	if (confirm('Do you really want to delete this ad?')) {
    		$.get( "deletead?id=" + id, function() {
				location.href="my-ads";
			});
     	} else {
     	    // Do nothing!
     	}
    }
    
    function deletePic(picid,adid)
    {
   		$.get( "deletepic?picid=" + picid + "&adid=" + adid, function() {
			location.reload();
		});
    }
</script>

<div class="reservation">

   <legend>Edit your ad</legend>


    <c:if test="${page_error != null }">
        <div class="alert alert-error">
            <button type="button" class="close" style="left:0;" data-dismiss="alert">&times;</button>
            <h4>Error!</h4>
            ${page_error}
        </div>
    </c:if>



    <form:form method="post" modelAttribute="adCreateForm" action="addupdate?${_csrf.parameterName}=${_csrf.token}&id=${idstring }"  id="addUpdateForm" cssClass="form-horizontal"
               autocomplete="off" enctype="multipart/form-data">

        <fieldset>
            <legend>General Info</legend>

            <c:set var="titleErrors"><form:errors path="title"/></c:set>
            <div class="control-group<c:if test="${not empty titleErrors}"> error</c:if>">
                    <label class="control-label" for="field-title">Title</label>
                    <div class="controls">
                    <form:input path="title" id="field-title" tabindex="2" maxlength="150" width="350px" value="${currentAd.title}"/>
                    <form:errors path="title" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <c:set var="roomDescErrors"><form:errors path="roomDesc"/></c:set>
            <div class="control-group<c:if test="${not empty roomDescErrors}"> error</c:if>">
                    <label class="control-label" for="field-roomDesc">Room Description</label>
                    <div class="controls">
                    <textarea name="roomDesc" id="field-roomDesc" rows="6"  width="350px" style="resize:vertical;" tabindex="2" maxlength="500">${currentAd.roomDesc}</textarea>
                    <form:errors path="roomDesc" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <c:set var="peopleDescErrors"><form:errors path="peopleDesc"/></c:set>
            <div class="control-group<c:if test="${not empty peopleDescErrors}"> error</c:if>">
                    <label class="control-label" for="field-peopleDesc">People Description</label>
                    <div class="controls">
                    <textarea name="peopleDesc" id="field-peopleDesc" rows="6" width="350px" style="resize:vertical;" tabindex="2" maxlength="500">${currentAd.peopleDesc}</textarea>
                    <form:errors path="peopleDesc" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <c:set var="numberOfPeopleErrors"><form:errors path="numberOfPeople"/></c:set>
            <div class="control-group<c:if test="${not empty numberOfPeopleErrors}"> error</c:if>">
                    <label class="control-label" for="field-numberOfPeople">Num. People</label>
                    <div class="controls">
                    <form:input path="numberOfPeople" id="field-numberOfPeople"  tabindex="2" maxlength="150" value="${currentAd.numberOfPeople}"/>
                    <form:errors path="numberOfPeople" cssClass="help-inline" element="span"/>
                </div>
            </div>


            <c:set var="roomSizeErrors"><form:errors path="roomSize"/></c:set>
            <div class="control-group<c:if test="${not empty roomSizeErrors}"> error</c:if>">
                    <label class="control-label" for="field-roomSize">Size</label>
                    <div class="controls">
                    <form:input path="roomSize" id="field-roomSize"  tabindex="2" maxlength="150" value="${currentAd.roomSize}"/> m&sup2;
                    <form:errors path="roomSize" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <c:set var="roomPriceErrors"><form:errors path="roomPrice"/></c:set>
            <div class="control-group<c:if test="${not empty roomPriceErrors}"> error</c:if>">
                    <label class="control-label" for="field-roomPrice">Price</label>
                    <div class="controls">
                    <form:input path="roomPrice" id="field-roomPrice"  tabindex="2" maxlength="150" value="${currentAd.roomPrice}"/> CHF
                    <form:errors path="roomPrice" cssClass="help-inline" element="span"/>
                </div>
            </div>

            <legend>Availability</legend>
            <c:set var="fromDateErrors"><form:errors path="fromDate"/></c:set>
            <div class="control-group<c:if test="${not empty fromDateErrors}"> error</c:if>">
                    <label class="control-label" for="field-fromDate">from</label>
                    <div class="controls">
                    <form:input path="fromDate" id="field-fromDate" class="span2" tabindex="2" maxlength="150" value="${fromDate}"/>
                    <form:errors path="fromDate" cssClass="help-inline" element="span"/>
                </div>      
            </div>

            <c:set var="toDateErrors"><form:errors path="toDate"/></c:set>
            <div class="control-group<c:if test="${not empty toDateErrors}"> error</c:if>">
                    <label class="control-label" for="field-toDate">till</label>
                    <div class="controls">
                    <form:input path="toDate" id="field-toDate" class="span2" tabindex="2" maxlength="150" value="${toDate}"/> or leave empty for undefined
                    <form:errors path="toDate" cssClass="help-inline" element="span"/>
                </div>
            </div>
            <%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
            <form:input type="hidden" id="field-username" path="username" value="<%=SecurityContextHolder.getContext().getAuthentication().getName()%>"/>
            <style type="text/css">
                #address_left {
                    width: 50%;
                    float:left;
                }
                #address_right {
                    width: 220px;
                    height: 200px;
                    float:left;
                    margin-left: 30px;
                }
                
            </style>
            <legend>Address</legend>
            <div id="address_left">
                <c:set var="streetErrors"><form:errors path="street"/></c:set>
                <div class="control-group<c:if test="${not empty streetErrors}"> error</c:if>">
                        <label class="control-label" for="field-street">Street</label>
                        <div class="controls">
                        <form:input path="street" onblur="codeAddress()" id="field-street" tabindex="2" maxlength="150" value="${currentAd.address.street}"/>
                        <form:errors path="street" cssClass="help-inline" element="span"/>
                    </div>
                </div>

                <c:set var="plzErrors"><form:errors path="plz"/></c:set>
                <div class="control-group<c:if test="${not empty plzErrors}"> error</c:if>">
                        <label class="control-label" for="field-plz">Postcode</label>
                        <div class="controls">
                        <form:input path="plz" onblur="codeAddress()" id="field-plz" tabindex="2" maxlength="150" value="${currentAd.address.plz}"/>
                        <form:errors path="plz" cssClass="help-inline" element="span"/>
                    </div>
                </div>

                <c:set var="cityErrors"><form:errors path="city"/></c:set>
                <div class="control-group<c:if test="${not empty cityErrors}"> error</c:if>">
                        <label class="control-label" for="field-city">City</label>
                        <div class="controls">
                        <form:input path="city" onblur="codeAddress()" id="field-city" tabindex="2" maxlength="150" value="${currentAd.address.city}"/>
                        <form:errors path="city" cssClass="help-inline" element="span"/>
                    </div>
                </div>
            </div>

            <div id="address_right">
                <style>
                    #map-canvas {
                        height: 100%;
                        widht: 100%;
                        margin: 0px;
                        padding: 0px
                    }
                </style>
                <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
                <script>
    var map;
    var geocoder;
    var marker;
    function initialize() {
        geocoder = new google.maps.Geocoder();
        var mapOptions = {
            zoom: 8,
            center: new google.maps.LatLng(46.9479222, 7.4446085, 7)
        };
        map = new google.maps.Map(document.getElementById('map-canvas'),
                mapOptions);
        codeAddress();
    }

    function codeAddress() {
        var address = document.getElementById("field-street").value + " " + document.getElementById("field-plz").value + " " + document.getElementById("field-city").value;
        geocoder.geocode({'address': address}, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                map.setCenter(results[0].geometry.location);
                if(marker==null)
                {
                	marker = new google.maps.Marker({
                    	map: map,
                    	position: results[0].geometry.location
                	});
            	}	
                else
                {
                	marker.setPosition(results[0].geometry.location);
                }
                map.setCenter(results[0].geometry.location);
                map.setZoom(16);
            } else {

            }
        });
    }

    google.maps.event.addDomListener(window, 'load', initialize);

                </script>
                <div id="map-canvas"></div>
            </div>

            <legend>Images</legend>
            
            <c:forEach items="${currentAd.pictures}" var="pic">
            	<img style="max-width:100px;height:auto;" data-id="${pic.id }" class="gallery" src="<c:url value="img/${pic.url}"/>"/>
            	<button class="btn" onclick="deletePic(${pic.id},${currentAd.id})">Delete Image</button>
            	<br />
            	<br />
        	</c:forEach>
            
            <div id="files">File to upload: <input type="file" name="files[0]"><br /> </div>

            <button type="button" class="btn"  value="Add another file" id="add">Add another File</button>

            <div id="result">${result}</div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Change</button>
                <button type="button" onclick="deleteAd(${currentAd.id})" class="btn btn-primary">Delete</button>
                <button type="button" class="btn" onclick="javascript:location.href='showad?value=${currentAd.id}'">Cancel</button>
            </div>
        </fieldset>
    </form:form>


	</div>
		</div>
		<div class="clear"></div>
		</div>
	

<c:import url="template/footer.jsp" />
