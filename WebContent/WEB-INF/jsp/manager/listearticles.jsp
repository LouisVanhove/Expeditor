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
											<form action="${pageContext.request.contextPath}/manager/EditArticle" method="post">
												<th><input class="btn btn-md btn-primary" type="submit" name="add" value="Ajouter"></th>
											</form>
										</tr>
									</thead>
								<tbody>
								
									<c:forEach var="article" items="${listarticles}">
										<tr>
											<form action="${pageContext.request.contextPath}/manager/EditArticle" method="post">
												<input type="hidden" name="id_article" value="${article.id}"/>
												<td scope="row">${article.id}</td>
												<td>${article.label}</td>
												<td>${article.description}</td>
												<td>${article.weight}</td>
												<td><input class="btn btn-md btn-primary" type="submit" name="modify" value="Modifier"></td>
											</form>
											<form id="formSuppression${article.id}" action="${pageContext.request.contextPath}/manager/DeleteArticle" method="POST">
												<input type="hidden" name="id_article" value="${article.id}"/>
												<input type="hidden" name="delete" value="delete"/>
											</form>
												<td><button class="btn btn-md btn-danger"  onclick="confirmArticleSuppression('formSuppression${article.id}')" value="${article.id}">Supprimer</button></td>
										</tr>	
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