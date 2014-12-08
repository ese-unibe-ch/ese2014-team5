<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:import url="template/header.jsp" />
	
<style>

.invit {
width: 300px;
	right: 0;
}

.leftdiv {
width: 300px;
 left: 0;
 float: left;
}

#enquiries > li 
{
	margin-bottom: 1em;
	width: 100%;
}

</style>	
	
<script type="text/javascript">

$(document).ready(function(){
	$(".lInv").parent().addClass("active");
	$.get("setenquirynotificationsread?id=${currentUser.id}", function(){
		
	});
});

function acceptinvitation(id)
{
	$.get( "acceptinvitation?id=" + id, function() {
		
	});
	location.reload();
}

function cancelinvitation(id)
{
	$.get( "cancelinvitation?id=" + id, function() {
		
	});
	location.reload();
}
</script>
<div class="reservation">
<legend>My sent enquiries</legend>

<div class="resultblock" id="sent_enquiries_list">
	<c:forEach items="${enquiriesList}" var="enq" varStatus="status">
		<div class="result">
			<div class="leftdiv"><a href="showad?value=${enq.advert.id}">${enq.advert.title}</a></div>
			<div class="invit">
				<c:if test="${enq.status=='ACCEPTED' }">You are invited on ${enq.invitation.fromDate }</c:if>
				<c:if test="${enq.invitation!=null && enq.status=='UNKNOWN'}">Invitation 
					<button class="btn btn-primary" onclick="acceptinvitation(${enq.id })">Accept</button>
					<button class="btn" onclick="cancelinvitation(${enq.id })">Reject</button>
				</c:if>  
				<c:if test="${enq.status=='CANCELLED' }">
					You have rejected the invitation.
				</c:if>
			</div>
		</div>
		<c:if test="${ ! status.last}" ><div class="resultseparator"></div>  </c:if>
	</c:forEach>
</div>
	
</div>


</div>
<div class="clear"></div>
</div>


<c:import url="template/footer.jsp" />