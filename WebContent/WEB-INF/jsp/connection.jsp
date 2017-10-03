<%@ page language="java" contentType="text/html; charset="UTF-8"    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/fragments/head.jsp"%>
<title>Connection</title>
</head>
<body>

<!-- debut section -->
	<section id="page_connexion" >
		<div class="container">
			
			<div class="row">
				<div class="col-separator col-xs-12 col-sm-6">
						<h4>Authentification</h4>

							<form class="connexion" action="<%= request.getContextPath() %>/connexion" method="post" data-toggle="validator" data-disable="false">
								<div class="form-group text-left">
									<input class="form-control" type="text" id="email" name="email" type="email" placeholder="Saisir votre identifiant" value="" required />
								</div>
								
								<div class="form-group text-left">
									<input class="form-control" type="password" id="motdepasse" name="motdepasse"
										placeholder="Saisir votre mot de passe"  value="" required/>
								</div>
								
								<div class="form-group text-left">
									<input class="btn btn-md btn-primary" type="submit" id="connexion" value="Connexion" />
								</div>
					
							</form>
						</div><!-- fin panel body -->

			</div><!-- fin row -->
			
			
		</div><!-- fin container --> 
	</section><!-- fin section -->

<c:import url="/WEB-INF/fragments/footer.jsp"/>
