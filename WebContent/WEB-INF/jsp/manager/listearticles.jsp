<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>

<!-- debut section -->
	<section id="page_connexion" class="section">
		<div class="container">
			
			<div class="row">
				<div class="col-sm-12 col-xs-12">
					<h2>Liste des articles</h2>
					
					<form action="${pageContext.request.contextPath}/utilisateur/gestionenfant" method="post">
					<div class=" add-task-row">
						<!-- Bouton ajouter -->
						<input type="submit" class="btn btn-md btn-primary" name="ajouter"
							value="Ajouter">
					</div>
					</form>

					<div class="panel-body">
						<c:forEach var="article" items="${listearticles}">
							<form action="${pageContext.request.contextPath}/utilisateur/gestionenfant" method="post">

							<div class="task-content">
								<ul class="list-group">
									<li>
										<div class="list-group-item">
												<span class="task-title-sp">${article.id}</span> 
												<span class="task-title-sp">${article.label}</span> 
												<span class="task-title-sp">${article.description}</span>
												<span class="task-title-sp">${article.weight}</span>

												<div class="pull-right hidden-phone">
													<!-- Bouton modifier -->
													<button class="buttonmodifier" name="modifier">
														<i class="fa fa-pencil"></i>
													</button>

													<!-- Bouton supprimer -->
													<button class="buttonsupprimer" name="supprimer">
														<i class="fa fa-trash-o"></i>
													</button>
												</div>
											</div>
									</li>
								</ul>
							</div>
						</form>
					</c:forEach>
					<br>
					<span class="message">${message}</span>
				</div>
						
				</div><!-- fin -
			</div><!-- fin row -->
			
		</div><!-- fin container --> 
	</section><!-- fin section -->



<c:import url="/WEB-INF/fragments/footer.jsp"/>