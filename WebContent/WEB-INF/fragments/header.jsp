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
   		 <nav class="navbar navbar-default navbar-fixed-top" id="mainNav">
      		<div class="container">
      			<div class="navbar-header">
      				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle Navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
       		 		<a class="navbar-brand" href="/"><h2>EXPEDITOR</h2> <h5>- Gestion de préparation de commandes</h5></a>
       		 	</div>
        		<div class="navbar-collapse collapse">
           			<ul class="nav navbar-nav navbar-right">
            			<li >${employee.name}</li>
            			<li><a href="<%=request.getContextPath()%>/deconnexion">Deconnexion</a></li>
          			</ul>
        		</div>
      		</div>
    	</nav> 


