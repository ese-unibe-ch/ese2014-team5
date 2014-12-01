<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="template/header.jsp" />
	

<div class="reservation">
<legend>Enquiries for my ad: ${ad.title}</legend>

<style>

	#calendar {
		max-width: 500px;
		margin: 0 0;
		width: 500px;
	}
	
	.fc-left > h2 {
	font-size: 12pt;
	}
	
	.selectedCell {
	background-color: rgba(200,200,255,100) !important;
	}
	
	#enquirylist {
		
	}
	
	#enquirylist > li {
		clear: left;
		width: 100%;
		height: 32px;
		border: #52ABDF solid 1px;
		border-radius: 4px;
		margin-bottom: 0.8em;
		padding: 0.4em;
	}
	
	#enquirylist > li > div {
		float:left;
		margin-right: auto;
		
	}
	
	.favorize
	{
		float:right !important;
	}
	
	.star 
	{
		width: 32px;
		height: 32px;
		background: url("web/images/icon/favorite.png") transparent;
		opacity: 0.3;
		float: left;
		margin-right: 3px;
	}
	
	.unknown 
	{
		width: 32px;
		height: 32px;
		background: url("web/images/icon/help.png");
		float: left;
		margin-right: 3px;
	}
	
	.accepted 
	{
		width: 32px;
		height: 32px;
		background: url("web/images/icon/accept.png");
		float: left;
		margin-right: 3px;
	}
	
	.cancelled 
	{
		width: 32px;
		height: 32px;
		background: url("web/images/delete/help.png");
		float: left;
		margin-right: 3px;
	}
	
	.star:last-child
	{
		clear:right;
	}

</style>
<script>
$(document).ready(function() {
	
		$( document ).tooltip();
		$("#calendar").fullCalendar({
			events:[
			        <c:forEach items="${invitationsList}" var="inv">
			        	<c:if test="${inv.cancelled == false}">
			        	{
			        		id: '${inv.id}',
			        		title  : 'Visit',
			                start  : '${inv.fromDate}'
			        	},
			        	</c:if>
			        </c:forEach>
			       ],
			       eventClick: function(calEvent, jsEvent, view) {

			           if (confirm('Do you really want to cancel this event?')) {
			        	   deleteEvent(calEvent.id);
			        	   $(this).fullCalendar( 'removeEvent', calEvent.id );
			        	} else {
			        	    // Do nothing!
			        	}
			       }
		});
		
		function deleteEvent(id)
		{
			$.get( "deleteinvitation?id=" + id, function() {
				location.reload();
			});
		}

		$("#datetime24").change(function(){
	
			$("#calendar").fullCalendar('gotoDate', $("#datetime24").data("value"));
		});
		
		$("#Button1").click( function () {
            $('#calendar').fullCalendar('next');
        });
        $("#Button2").click( function () {
            $('#calendar').fullCalendar('prev');
        });

        $('#time').combodate({
	        firstItem: 'name', //show 'hour' and 'minute' string at first item of dropdown
	        minuteStep: 1
	    });
        $('#datetime24').combodate({
        	minYear: 2014,
            maxYear: 2020
        });
        $('#userList').combodate({
        	minYear: 2014,
            maxYear: 2020
        });
        
        var selected_enqlist = [];
        
        $(".not_invited").click(function(){
        	index = selected_enqlist.indexOf($(this).data("enqid"));
        	if (index > -1) {
        		selected_enqlist.splice(index, 1);
        		$(this).css("background-color", "white");
        	}
        	else
        	{
        		$(this).css("background-color", "#AAAAFF");
        		selected_enqlist.push($(this).data("enqid"));
        	}
        	
        	$("#selected_enquiries").val(selected_enqlist);
        	//selected_enqlist
        });
        
        $(".star_1").css("opacity", "1.0");
		$(".star").hover(function(){
			var i,a;
			a = $(this).data("number");
			var parent = $(this).parent();
			for(i=2;i<=5;i++)
			{
				if(i<=a)
				{
					$(parent).children(".star_" + i).css("opacity", "1.0");
				}
				else
				{
					$(parent).children(".star_" + i).css("opacity", "0.3");
				}
			}
		});
		
		$(".favorize").mouseleave(function(){
			
			var i,a;
			a = $(this).data("amount");
			for(i=2;i<=5;i++)
			{
				if(i<=a)
				{
					$(this).children(".star_" + i).css("opacity", "1.0");
				}
				else
				{
					$(this).children(".star_" + i).css("opacity", "0.3");
				}
			}
		});
	
		$(".star").click(function(){
			$(this).parent().data("amount",$(this).data("number"));
			// USE THIS TRIGGER TO UPDATE DATABASE!!!
			$.get( "setrank?id=" + $(this).parent().data("id") + "&rank=" + $(this).data("number"), function() {
				location.reload();
			});
		});
		
		$( ".favorize" ).each(function( index ) {
			var i,a;
			a = $(this).data("amount");
			for(i=2;i<=5;i++)
			{
				if(i<=a)
				{
					$(this).children(".star_" + i).css("opacity", "1.0");
				}
				else
				{
					$(this).children(".star_" + i).css("opacity", "0.3");
				}
			}
		});
        
	});
	</script>
	
	<input id="Button2" type="button" value="prev" />
	<input id="Button1" type="button" value="next" />
<div id="calendar"></div>
	<form:form modelAttribute="invitationForm" cssClass="form-horizontal" autocomplete="off" action="invite" method="post">
		<form:input path="advertId" type="hidden" value="${ad.id }"/>
		<form:input path="userFromId" type="hidden" value="${currentUser.id }"/>
		<label class="control-label" for="field-message">Message</label>
  
        	<textarea name="textOfInvitation" id="field-message" rows="6" width="350px" style="resize:vertical;" tabindex="2" maxlength="500"></textarea>
<br/>
    	<label class="control-label" for="field-dateFrom">From</label>

        	<form:input path="fromDate" type="text" id="datetime24" data-format="YYYY-MM-DD HH:mm" data-template="DD / MM / YYYY     HH : mm" name="datetime" value="2015-01-01 00:00"/>
		<br/>
    	<label class="control-label" for="field-fromTime">Duration</label>

        	<form:input type="text" id="time" path="duration" data-format="HH:mm" data-template="HH : mm" name="datetime"/>
		<br/>
    	<form:input type="hidden" path="selected_enquiries" name="selected_enquiries" id="selected_enquiries"/> 
    	<label class="control-label" for="field-userList">Select candidates</label>
		<br/>
			<ul id="enquirylist">
				<c:forEach items="${enqlist}" var="enquiry">
					<fmt:formatDate value="${enquiry.invitation.fromDate}" var="formatDate" pattern="MM/dd/yyyy HH:mm" />
					<c:if test="${enquiry.invitation !=null}">
						<li class="enqlist_item_invited enqlist_item"><div>${enquiry.userFrom.firstName} ${enquiry.userFrom.lastName} ${formatDate }</div>
							<c:if test="${enquiry.status == 'ACCEPTED'}">
								<div class="accepted" title="The Invitation has been accepted."></div>
							</c:if>
							<c:if test="${enquiry.status == 'CANCELLED'}">
								<div class=cancelled title="The Invitation was not accepted."></div>
							</c:if>
						    <c:if test="${enquiry.status == 'UNKNOWN'}">
								<div class="unknown" title="An Invitation has been sent, and is waiting to be answered."></div>
							</c:if>
							
							<div class="favorize" data-amount="${enquiry.rating }" data-id="${enquiry.id }">
								<div class="star star_5" data-number="5"></div>
								<div class="star star_4" data-number="4"></div>
								<div class="star star_3" data-number="3"></div>
								<div class="star star_2" data-number="2"></div>
								<div class="star star_1" data-number="1"></div>
							</div>
						</li>
						</c:if>
					<c:if test="${enquiry.invitation ==null}">
						<li title="Click to select this user for invitation." class="enqlist_item not_invited" data-enqid="${ enquiry.id}">${enquiry.userFrom.firstName} ${enquiry.userFrom.lastName}</li>
					</c:if>
				</c:forEach>
			</ul>
		<br>
		<div class="form-actions">
      	  <button type="submit" class="btn btn-primary">Send Invitation</button>
      	</div>
		    
	</form:form>
</div>

</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />