<!DOCTYPE html>
<html lang="en">
<head>

	<title>WG-Site</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700' rel='stylesheet' type='text/css'>
	<link href="web/css/style.css" rel="stylesheet" type="text/css" media="all" />
	<script src="web/js/jquery.min.js"></script>
	<!--start slider -->
	<link rel="stylesheet" href="web/css/fwslider.css" media="all">
	<script src="web/js/jquery-ui.min.js"></script>
	<script src="web/js/css3-mediaqueries.js"></script>
	<script src="web/js/fwslider.js"></script>
	<!--end slider -->
	<!---strat-date-piker---->
	<link rel="stylesheet" href="web/css/jquery-ui.css" />
	<script src="web/js/jquery-ui.js"></script>
			  <script>
					  $(function() {
					    $( "#datepicker,#datepicker1" ).datepicker();
					  });
			  </script>
	<!---/End-date-piker---->
	<link type="text/css" rel="stylesheet" href="web/css/JFGrid.css" />
	<link type="text/css" rel="stylesheet" href="web/css/JFFormStyle-1.css" />
			<script type="text/javascript" src="web/js/JFCore.js"></script>
			<script type="text/javascript" src="web/js/JFForms.js"></script>
			<!-- Set here the key for your domain in order to hide the watermark on the web server -->
			<script type="text/javascript">
				(function() {
					JC.init({
						domainKey: ''
					});
					})();
			</script>
	<!--nav-->
	</head>

    <link rel="stylesheet" type="text/css" href="css/main.css" media="all"/>
   <!--  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">-->
    <script type="text/javascript"  src="dn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-2.1.1.min.js"></script>

	<script type="text/javascript">
	$(document).ready(function() {
		  $(document).on("click", ".close", function(){
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
	</script>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
<!-- start header -->
<div class="header_bg">
<div class="wrap">
	<div class="header">
		<div class="logo1">
			<a href="index"><h3 class="logotext">WG-Site</h3></a>
		</div>
		<div class="h_right">
			<!--start menu -->
			<ul class="menu">
				<li class="active"><a href="index">Home</a></li> |
				<li><a href="login">login</a></li> |
				<li><a href="register">register</a></li> |
				<li><a href="profile">profile</a></li> |
				<li><a href="adcreation">create an Ad</a></li>
				<div class="clear"></div>
			</ul>
			<!-- start profile_details --><div style="width:100px;position:absolute;right:20px;left:auto;">
					<form class="style-1 drp_dwn">
						<!-- <div class="row">
							<div class="grid_3 columns"> -->
								<select class="custom-select" id="select-1">
									<option selected="selected">EN</option>
									<option>DE</option>
								</select>
						<!--	</div>		
						</div>		 -->
					</form>
					</div>
		</div>
		<div class="clear"></div>
		<div class="top-nav">
		<nav class="clearfix">
				<ul>
				<li class="active"><a href="index">Home</a></li>
				<li><a href="login">login</a></li>
				<li><a href="register">register</a></li>
				<li><a href="adcreation">Create an Ad</a></li>
				</ul>
				<a href="#" id="pull">Menu</a>
			</nav>
		</div>
	</div>
</div>
</div>
<div class="topbox"><img src="web/images/slum.jpg"/></div>

<div id="container">
<div class="wrap">

	<div class="online_reservation">
	<div class="b_room">
		    <div class="main_bg">
		           