<!DOCTYPE html>
<html lang="en">
<head>

	<title>RoomFinder</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700' rel='stylesheet' type='text/css'>
	<link href="web/css/style.css" rel="stylesheet" type="text/css" media="all" />
	<!--start slider -->
	<script src="web/js/jquery-2.1.1.js"></script>
	<script src="web/js/css3-mediaqueries.js"></script>
	<!--end slider -->
	  
  <script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
  <!-- <link id="bs-css" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet"> -->
<!-- 	<link type="text/css" rel="stylesheet" href="web/css/JFGrid.css" /> -->
	<link type="text/css" rel="stylesheet" href="web/css/JFFormStyle-1.css" />
			<script type="text/javascript" src="web/js/JFCore.js"></script>
			<script type="text/javascript" src="web/js/JFForms.js"></script>
			<script type="text/javascript" src="web/js/moment.min.js"></script>
			<script type="text/javascript" src="web/js/combodate.js"></script>
			<!-- Set here the key for your domain in order to hide the watermark on the web server -->
	<!--nav-->

    <link rel="stylesheet" type="text/css" href="css/main.css" media="all"/>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<!--  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">-->
    <script type="text/javascript"  src="dn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link  href="http://fotorama.s3.amazonaws.com/4.6.2/fotorama.css" rel="stylesheet"> <!-- 3 KB -->
	<script src="http://fotorama.s3.amazonaws.com/4.6.2/fotorama.js"></script> <!-- 16 KB -->
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.2.2/fullcalendar.min.js"></script>
<link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.2.2/fullcalendar.min.css">
<link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.2.2/fullcalendar.print.css">

	<script type="text/javascript">
	
	var unread_enquiries = 0;
	var unread_bookmarks = 0;
	var unread_searches = 0;
	var unread_invitations = 0;
	
	$(document).mouseup(function (e)
	{
	    var container = $("#notifications");

	    if (!container.is(e.target) // if the target of the click isn't the container...
	        && container.has(e.target).length === 0) // ... nor a descendant of the container
	    {
	        container.hide(200);
	    }
	});
	
	$(document).ready(function() {
	
		  $(document.body).on("click", ".close", function(){
			 $(this).parent().remove();
		  });
		  	
		  var pull 		= $('#pull');
			menu 		= $('nav ul');
			menuHeight	= menu.height();

		$(pull).on('click', function(e) {
			e.preventDefault();
			menu.slideToggle();
		});

		$(window).resize(function(){
  		var w = $(window).width();
  		if(w > 320 && menu.is(':hidden')) {
  			menu.removeAttr('style');
  		}
		});
	});
	
	function setreadBookmark(id, url)
	{
		$.get( "setreadbookmark?id=" + id, function() {
			
		});
		
		if(unread_bookmarks>0)
		{
			unread_bookmarks--;
			$(".bBookmarks").text("Bookmarks ("+unread_bookmarks+")");
		}
		else
			$(".bBookmarks").text("Bookmarks");
		
		if(url!="")
			window.location.href=url;
	}

	function setread(id,url)
	{
		
  		if(unread_searches>0)
  		{
	  		unread_searches--;
  			$(".bSearches").text("Saved Searches ("+unread_searches+")");
  		}
  		else
  			$(".bSearches").text("Saved Searches");
  		if(unread_enquiries>0)
  		{
  			unread_enquiries--;
	  		$(".bEnquiries").text("My Ads ("+unread_enquiries+")");
  		}
  		else
  			$(".bEnquiries").text("My Ads");
  		if(unread_invitations>0)
  		{
  			unread_invitations--;
	  		$(".bInvitations").text("Enquiries ("+unread_invitations+")");
  		}
  		else
  			$(".bInvitations").text("Enquiries");
  		
  		if(url!="")
			window.location.href=url;
	}
/*	$.get("getnotifications.htm", function(data){
		alert(data);
	});*/
	
	$.getJSON( "getnotifications.htm", function( data ) {
	  	
	  	
	  	//var items = [];
	  	
	  	data["Notifications"].forEach(function(entry) {
	  		if(entry.read==0)
	  			{
	  				switch(entry.type)
	  				{
	  					case "ENQUIRY":
	  						unread_enquiries++;
	  						break;
	  					case "BOOKMARK":
	  						unread_bookmarks++;
	  						break;
	  					case "INVITATION":
	  						unread_invitations++;
	  						break;
	  					case "SEARCHMATCH":
	  						unread_searches++;
	  						break;
	  					default:
	  						break;
	  				}
		  			/*items.push( "<li id='" + entry.id + "'><a onclick='setread(" + entry.id + ", \""+ entry.url +"\")'>" + entry.text + "</a></li>" );
	  				unread_elements++;*/
	  			}
		  		else
		  		{
		  			
		  		}
	  		
	  		if(unread_bookmarks>0)
		  		$(".bBookmarks").text("Bookmarks ("+unread_bookmarks+")");
	  		if(unread_searches>0)
		  		$(".bSearches").text("Saved Searches ("+unread_searches+")");
	  		if(unread_enquiries>0)
		  		$(".bEnquiries").text("My Ads ("+unread_enquiries+")");
	  		if(unread_invitations>0)
		  		$(".bInvitations").text("Enquiries ("+unread_invitations+")");
	  				//items.push( "<li id='" + entry.id + "'><a onclick='"+ entry.url +"'>" + entry.text + "</a></li>" );
	  	});
	    
	 });
   
	</script>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" style="display:none;" id="logoutForm">
	<input type="submit" value="logout" />
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<!-- start header -->

<div class="header_bg">
<div class="wrap">

	<div class="header">
		<div class="logo">
 			Room Finder
		</div>
		<div class="h_right">
			<!--start menu -->
			<ul class="menu">
				<li><a class="lHome" href="index">Home</a></li>
				<sec:authorize access="hasRole('ROLE_USER')">
<!-- 					<li class="menu-item"> -->
<!-- 						<a href="#">Profile</a> -->
<!-- 						<ul class="menu"> -->
<%-- 							<li><a href="profile?name=<%=SecurityContextHolder.getContext().getAuthentication().getName()%>"><span id="username">Profile</span></a></li> | --%>
<!-- 							<li><a href="saved-searches"><span id="username">Saved Searches</span></a></li> | -->
<!-- 							<li><a href="my-ads">My Ads</a></li> -->
<!-- 						</ul> -->
<!-- 					</li> -->
				    <li><a class="lBook bBookmarks" href="bookmarks">Bookmarks</a></li>
				    <li><a class="lInv bInvitations" href="sentenquiries">Enquiries</a></li>
					<li><a class="lProf" href="profile?name=<%=SecurityContextHolder.getContext().getAuthentication().getName()%>">Profile</a></li>
					<li><a class="lSear bSearches" href="saved-searches">Saved Searches</a></li>
					<li><a class="lEnq bEnquiries" href="my-ads">My Ads</a></li>
					<li><c:if test="${pageContext.request.userPrincipal.name != null}">
							<a style="cursor:pointer;" onclick="javascript:logout();">Logout</a>
						</c:if></li>
					
				</sec:authorize>
				<sec:authorize access="!hasRole('ROLE_USER')">
					<li><a class="lLog" href="login">login</a></li> |
					<li><a class="lReg" href="register">register</a></li>
				</sec:authorize>
				<div class="clear"></div>
			</ul>
			<!--<form class="style-1 drp_dwn">
				<div class="row">
					<div class="grid_3 columns">
						<select class="custom-select" id="select-1" style="display: none;">
							<c:forEach items="${notifications}" varStatus="loopCount" var="notif">
								<option>${notif.text }</option>
							</c:forEach>
							<div style="display:none"><option selected="selected"> </option></div>
						</select>
					</div>		
				</div>		
			</form>-->
			
		</div>
		
		<div class="clear"></div>
		
		<div class="top-nav">
		<nav class="clearfix">
				<ul>
				<li><a class="lHome" href="index">Home</a></li>
				<sec:authorize access="hasRole('ROLE_USER')">
					<li><a class="lBook bBookmarks" href="bookmarks">Bookmarks</a></li>
				    <li><a class="lInv bInvitations" href="sentenquiries">Enquiries</a></li>
					<li><a class="lProf" href="profile?name=<%=SecurityContextHolder.getContext().getAuthentication().getName()%>"><span id="username">Profile</span></a></li>
					<li><a class="lSear bSearches" href="saved-searches"><span id="username">Saved Searches</span></a></li>
					<li><a class="lEnq bEnquiries" href="my-ads">My Ads</a></li>
					<li><a style="cursor:pointer;" onclick="javascript:logout();">Logout</a></li>
				</sec:authorize>
				<sec:authorize access="!hasRole('ROLE_USER')">
					<li><a class="lLog" href="login">login</a></li>
					<li><a class="lReg" href="register">register</a></li>
				</sec:authorize>
				</ul>
				
				<a href="#" id="pull">Menu</a>
			</nav>
		</div>
		
	</div>
</div>
<sec:authorize access="hasRole('ROLE_USER')">
</sec:authorize>
</div>

<script type="text/javascript">
function logout() {
	document.getElementById("logoutForm").submit();
}
</script>
<div class="topbox"><img src="web/images/slum.jpg" onclick="getNotes()"/></div>

<div id="container">
<div class="wrap">

	<div class="online_reservation">
	<div class="b_room">
		    <div class="main_bg">
		           
		           