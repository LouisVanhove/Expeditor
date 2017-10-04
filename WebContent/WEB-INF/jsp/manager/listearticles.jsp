<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

<!-- debut section -->
	<section id="page_articles" class="section">
		<div class="container">
			
			<div class="row">
				<div class="col-sm-12 col-xs-12">
					<h3>Gestion Articles</h3>
					
					<c:if test="${listarticles.size() < 1}">
						<div id="ligne-article">
							<span>Il n'y a aucun article dans la base de données pour le moment.</span>
						</div>
					</c:if>
					
					<c:if test="${listarticles.size() > 0}">
						<div class="panel-body">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>Référence</th>
											<th>Label</th>
											<th>Description</th>
											<th>Poids</th>
											<th></th>
											<form action="${pageContext.request.contextPath}/manager/gererarticles" method="post">
												<th><input class="btn btn-md btn-primary" type="submit" name="add" value="Ajouter"></th>
											</form>
										</tr>
									</thead>
								<tbody>
								
									<c:forEach var="article" items="${listarticles}">
										<form action="${pageContext.request.contextPath}/manager/gererarticles" method="post">
											<tr>
												<input type="hidden" id="article" name="${article}"/>
												<td scope="row">${article.id}</td>
												<td>${article.label}</td>
												<td>${article.description}</td>
												<td>${article.weight}</td>
												<td><input class="btn btn-md btn-primary" type="submit" name="modify" value="Modifier"></td>
												<td><input class="btn btn-md btn-danger" type="submit" name="delete" value="Supprimer"></td>
											</tr>
										</form>
									</c:forEach>
								</tbody>
							</table>
							<span class="message">${message}</span>
						</div>
					</c:if>
						
				</div><!-- fin -
			</div><!-- fin row -->
			
		</div><!-- fin container --> 
	</section><!-- fin section -->



<c:import url="/WEB-INF/fragments/footer.jsp"/>