<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/fragments/head.jsp"%>
<title>Expeditor - &copy;lalateam</title>
</head>
<body>
<!-- Header -->
<header>
    <div class="bandeau-nav">
    	<a id="mobile-nav" class="menu-nav" href="#menu-nav"></a>
        
        <div id="logo">
        	<h4>EXPEDITOR - Gestion de préparation de commandes</h4>
        </div>
        
        <c:if test="${!empty User}">
        	<nav id="menu">
        		<ul id="menu-nav">
            		<li ><span class="glyphicon glyphicon-user"></span> ${User.profile.toString()} : ${User.firstName} ${User.lastName}</li>
            		<li><a href="${pageContext.request.contextPath}/deconnexion">Deconnexion</a></li>
            	</ul>
        	</nav>
        </c:if>
        
    </div>
</header>
<!-- End Header -->

