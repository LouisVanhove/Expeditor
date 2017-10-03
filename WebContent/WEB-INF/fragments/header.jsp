<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/fragments/head.jsp"%>
<title>Titre page</title>
</head>
<body>

	<!--Header-->
	<header>
		<div class="overlay-img"><a class="accueil" href="/">EXPEDITOR - Gestion de préparation de commandes </a></div>

		<nav class="mainmenu navbar navbar-default navbar-fixed-top">
       
       		<div class="container">
				<div class="row">
                	<div class="navbar-header">
                		<div class="collapse navbar-collapse" id="menu_accueil">
   					 		<ul class="nav navbar-nav pull-right">
   					 			<li>${employee.name}</li>
								<li><a href="<%=request.getContextPath()%>/deconnexion">Deconnexion</a></li>
							</ul>
						</div>
  					</div><!-- fin navbar-header -->	
				</div>	<!-- fin row -->		
        	</div><!-- fin container -->
    	</nav>
	</header>

