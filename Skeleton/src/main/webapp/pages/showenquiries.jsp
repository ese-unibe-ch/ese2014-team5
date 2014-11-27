<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
	

<div class="reservation">
<legend>Enquiries for my ad: ${ad.title}</legend>

<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		max-width: 200px;
		margin: 0 0;
		width: 200px;
	}
	
	.fc-left > h2 {
	font-size: 12pt;
	}
	
	.selectedCell {
	background-color: rgba(200,200,255,100) !important;
	}

</style>
<script>
$(document).ready(function() {

		$("#calendar").fullCalendar({
			/*defaultDate: '2014-11-12',
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			events: [
				{
					title: 'All Day Event',
					start: '2014-11-01'
				}
			],*/
		});

		$("td.fc-day-number").click(function(){
			$("td.fc-day-number").removeClass("selectedCell");
			 $(this).addClass("selectedCell");
			 $("#datetime24").combodate('setValue', $(this).data("date") + " 12:00");
		});
		
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
        
        $(".enqlist_item").click(function(){
        	$(this).css("background-color", "blue");
        	//selected_enqlist
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
        <div class="controls">
        	<textarea name="textOfInvitation" id="field-message" rows="6" width="350px" style="resize:vertical;" tabindex="2" maxlength="500"></textarea>
    	</div>
    	<label class="control-label" for="field-dateFrom">From</label>
    	<div class="controls">
        	<form:input path="fromDate" type="text" id="datetime24" data-format="YYYY-MM-DD HH:mm" data-template="DD / MM / YYYY     HH : mm" name="datetime" value="2015-01-01 00:00"/>
    	</div>
    	<label class="control-label" for="field-fromTime">Duration</label>
        <div class="controls">
        	<form:input type="text" id="time" path="duration" data-format="HH:mm" data-template="HH : mm" name="datetime"/>
    	</div>
    	
    	<label class="control-label" for="field-userList">Select candidates</label>
    	<div class="controls">
			<ul>
				<c:forEach items="${enqlist}" var="enquiry">
					<c:if test="${enquiry.invitation !=null}">
						<li class="enqlist_item" data-user="${ enquiry.userFrom.id}">${enquiry.userFrom.firstName} ${enquiry.userFrom.lastName} ${enquiry.invitation.fromDate}</li>
						</c:if>
					<c:if test="${enquiry.invitation ==null}">
						<li class="enqlist_item" data-user="${ enquiry.userFrom.id}">${enquiry.userFrom.firstName} ${enquiry.userFrom.lastName}</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		    
	</form:form>
</div>

</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />