<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/fragments/head.jsp"%>
<title>Titre page</title>
</head>
<body>

	
		<!-- Navigation -->
   		 <nav class="navbar navbar-inverse navbar-fixed-top" id="mainNav">
      		<div class="container">
      			<div class="navbar-header">
       		 		<a class="navbar-brand" href="/">EXPEDITOR - Gestion de préparation de commandes</a>
       		 	</div>
        		<div id="navbar" class="navbar-collapse collapse">
           			<ul class="nav navbar-nav">
            			<li class="nav-item">${employee.name}</li>
            			<li class="nav-item">
              				<a href="<%=request.getContextPath()%>/deconnexion">Deconnexion</a>
           		 		</li>
          			</ul>
        		</div>
      		</div>
    	</nav> 

	<!--Header-->
	<header>
	<p>houhou</p>
	</header>

