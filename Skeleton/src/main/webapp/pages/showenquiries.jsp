<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:import url="template/header.jsp" />
	

<div class="reservation">
<script>
$(document).ready(function(){
	$(".lEnq").parent().addClass("active");
});
</script>
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

	
	.favorize
	{
		float:right !important;
	}
	
	.star 
	{
		width: 24px;
		height: 24px;
		background: url("web/images/icon/favorite.png") transparent;
		background-size: 24px 24px;
		opacity: 0.1;
		float: left;
		margin-right: 3px;
		
	}
	
	.unknown 
	{
		width: 28px;
		height: 28px;
		background: url("web/images/icon/help.png");
		background-size: 28px 28px;
		float: left;
		margin-right: 3px;
		margin-left: 50%;
	}
	
	.accepted 
	{
		width: 24px;
		height: 24px;
		background: url("web/images/icon/accept.png");
		background-size: 24px 24px;
		float: left;
		margin-right: 3px;
		margin-left: 50%;
	}
	
	.cancelled 
	{
		width: 24px;
		height: 24px;
		background: url("web/images/icon/delete.png");
		background-size: 24px 24px;
		float: left;
		margin-right: 3px;
		margin-left: 50%;
	}
	
	.star:last-child
	{
		clear:right;
	}
	
	.indented {
		margin-left: 5em;
	}
	
	.result
	{
		min-height: 50px;
	}

</style>
<script>
$(document).ready(function() {
	
		$( document ).tooltip();
		$("#calendar").fullCalendar({
			events:[
			        <c:forEach items="${invitationsList}" var="inv">
			        	{
			        		id: '${inv.id}',
			        		title  : 'Visit',
			                start  : '${inv.fromDate}'
			        	},
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
	        minuteStep: 15,
	        firstItem: 'none'
	    });
        $('#datetime24').combodate({
        	firstItem: 'none',
        	minYear: 2014,
            maxYear: 2020,
            minuteStep: 15
        });

        
        var selected_enqlist = [];
        
        $(".not_invited").click(function(){
        	index = selected_enqlist.indexOf($(this).data("enqid"));
        	if (index > -1) {
        		selected_enqlist.splice(index, 1);
        		$(this).css("background-color", "white");
        		$(this).attr("title","Click to select this user for invitation.");
        	}
        	else
        	{
        		$(this).css("background-color", "#AAAAFF");
        		selected_enqlist.push($(this).data("enqid"));
        		$(this).attr("title", "Click to remove this user from the invitation selection.");
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
					$(parent).children(".star_" + i).css("opacity", "0.1");
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
					$(this).children(".star_" + i).css("opacity", "0.1");
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
					$(this).children(".star_" + i).css("opacity", "0.1");
				}
			}
		});
        
	});
	</script>
	<c:if test="${not empty invitationsList }">
		<button id="Button2" type="button" value="prev" class="btn">Previous</button>
		<button id="Button1" type="button" value="next" class="btn" >Next</button>
		<div id="calendar"></div>
	</c:if>
	<form:form modelAttribute="invitationForm" cssClass="form-horizontal" autocomplete="off" action="invite" method="post">
		<form:input path="advertId" type="hidden" value="${ad.id }"/>
		<form:input path="userFromId" type="hidden" value="${currentUser.id }"/>
		<label class="control-label" for="field-message">Message</label>
  
        	<textarea name="textOfInvitation" id="field-message" rows="6" width="350px" style="resize:vertical;" tabindex="2" maxlength="500"></textarea>
<br/>
    	<label class="control-label" for="field-dateFrom">From</label><br/><br />

        	<form:input path="fromDate" type="text" id="datetime24" data-format="YYYY-MM-DD HH:mm" data-template="DD / MM / YYYY     HH : mm" name="datetime" value="2015-01-01 10:15"/>
		<br/>
    	<label class="control-label" for="field-fromTime">Duration</label><br/><br />
        	<form:input type="text" id="time" path="duration" data-format="HH:mm" data-template="HH : mm" name="datetime" value="01:30"/>
		<br/>
    	<form:input type="hidden" path="selected_enquiries" name="selected_enquiries" id="selected_enquiries"/> 
    	<label class="control-label" for="field-userList">Select candidates</label>
		<br/>
			<div class="results" class="indented" >
				<c:forEach items="${enqlist}" var="enquiry" varStatus="status">
					<fmt:formatDate value="${enquiry.invitation.fromDate}" var="formatDate" pattern="MM/dd/yyyy HH:mm" />
					<c:if test="${enquiry.invitation !=null}">
						<div class="enqlist_item_invited enqlist_item result"><div>${enquiry.userFrom.firstName} ${enquiry.userFrom.lastName} ${formatDate }</div>
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
						</div>
						</c:if>
					<c:if test="${enquiry.invitation ==null}">
						<div title="Click to select this user for invitation." class="result enqlist_item not_invited" data-enqid="${ enquiry.id}">${enquiry.userFrom.firstName} ${enquiry.userFrom.lastName}</div>
					</c:if>
					<c:if test="${ ! status.last}" ><div class="resultseparator"></div>  </c:if>
				</c:forEach>
			</div>
		<div class="form-actions">
      	  <button type="submit" class="btn btn-primary">Send Invitation</button>
      	</div>
		    
	</form:form>
</div>

</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />