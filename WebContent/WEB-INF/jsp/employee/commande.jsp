<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
		<script type="javascript">
			var TotalLineNb =  ${sessionScope.currentOrder.listArticles.size()};
		</script>

<!-- debut section -->
	<section id="page_articles" class="section">
		<div class="container">
			<div class="row">
				<div class="col-sm-10 col-sm-push-1">
					<div class=" col-sm-10 col-sm-push-1 recap-client">
					
						<div class="row">
							<div class="col-sm-5"><h4>Commande n° ${sessionScope.currentOrder.id }</h4></div>
							<div class="col-sm-2"><h4>Adresse : </h4></div>
							<div class="col-sm-2"><h4>${sessionScope.currentOrder.customer.address }</h4></div>
						</div><!-- fin row -->
					
						<div class="row">
							<div class="col-sm-7"><h4>Client : ${sessionScope.currentOrder.customer.name }</h4></div>
							<div class="col-sm-5"><h4>${sessionScope.currentOrder.customer.zipCode } ${sessionScope.currentOrder.customer.city }</h4></div>
						</div><!-- fin row -->
					</div>
					
					<table class="table">
						<thead>
							<tr>
								<th>Référence</th>
								<th>Désignation</th>
								<th>Quantité demandée</th>
								<th>Quantité colis</th>
								<th>Etat</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="article" items="${sessionScope.currentOrder.listArticles}">												
							<tr id="line_${article.id}" class="uncompleted-line">
								<td scope="row">${article.id}</td>
								<td>${article.label }</td>
								<td>${article.quantity}</td>
								<td><input type="number" id="article_${article.id}" value="0" name="quantity" min="0" onkeyup="setArticleLineState(${article.id}, ${article.quantity})" onmouseup="setArticleLineState(${article.id}, ${article.quantity})" onblur="setArticleLineState(${article.id}, ${article.quantity})" max="${article.quantity}"></td>
								<td><span id="icon_${article.id}" class="glyphicon glyphicon-remove"></span></td>
							</tr>							
							</c:forEach>
						</tbody>
					</table>
					
					<div class="row">
						<div class="col-sm-1 col-sm-push-8">
							<!-- <a href="${pageContext.request.contextPath}/employee/BonLivraison" target="blank" class="btn btn-default">Imprimer Bon de Livraison</a> -->
							<button id="deliveryBtn" class="btn btn-default"  onclick="goDeliveryNote('${pageContext.request.contextPath}/employee/BonLivraison')" disabled>Bon de Livraison</button>
						</div>
				
						<div class="col-sm-1 col-sm-push-9">
							<form action="${pageContext.request.contextPath}/employee/Commande" method="POST">
								<input type="submit" id="nextOrder" class="btn btn-default" value="Commande Suivante" disabled/>
							</form>
						</div>
					</div><!-- fin row -->
				</div>
		</div><!-- fin row -->
	</div><!-- fin container --> 
</section><!-- fin section -->

<c:import url="/WEB-INF/fragments/footer.jsp"/>