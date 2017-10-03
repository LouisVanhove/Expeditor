<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>

<!-- debut section -->
	<section id="page_connexion" class="section">
		<div class="container">
			
			<div class="row">
				<div class="col-separator col-xs-12 col-sm-6">
						<h4>Authentification</h4>

							<form class="connexion" action="<%= request.getContextPath() %>/connexion" method="post" data-toggle="validator" data-disable="false">
								<div class="form-group text-left">
									<input class="form-control" type="text" id="login" name="login" placeholder="Saisir votre identifiant" value="" required />
									<span class="message">${erreurs['login']}</span>
								</div>
								
								<div class="form-group text-left">
									<input class="form-control" type="password" id="password" name="password"
										placeholder="Saisir votre mot de passe"  value="" required/>
									<span class="message">${erreurs['password']}</span>
								</div>
								
								<div class="form-group text-left">
									<input class="btn btn-md btn-primary" type="submit" id="connexion" value="Connexion" />
								</div>
								
								<span class="message">${message}</span>
					
							</form>
						</div><!-- fin panel body -->

			</div><!-- fin row -->
			
			
		</div><!-- fin container --> 
	</section><!-- fin section -->

<c:import url="/WEB-INF/fragments/footer.jsp"/>
