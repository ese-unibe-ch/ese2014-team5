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
			 $(this).addClass("selectedCell");
		});
		
		$("#Button1").click( function () {
            $('#calendar').fullCalendar('next');
        });
        $("#Button2").click( function () {
            $('#calendar').fullCalendar('prev');
        });
	});
	</script>
	
	<input id="Button2" type="button" value="prev" />
	<input id="Button1" type="button" value="next" />
<div id="calendar"></div>
</div>


</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />