<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>

	
	<div class="row">
		<div class="col-sm-10 col-sm-push-1">
			<div class="jumbotron col-sm-10 col-sm-push-1 recap-client">
				<div class="row">
					<div class="col-sm-5 col-sm-push-1">Commande n° ${sessionScope.currentOrder.id }</div>
					<div class="col-sm-1">Adresse : </div>
					<div class="col-sm-5">${sessionScope.currentOrder.customer.address }</div>
				</div>
				
				<div class="row">
					<div class="col-sm-6 col-sm-push-1">Client : ${sessionScope.currentOrder.customer.name }</div>
					<div class="col-sm-5">${sessionScope.currentOrder.customer.zipCode } ${sessionScope.currentOrder.customer.city }</div>
				</div>
			</div>
			
			<table class="table table-hover">
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
						<tr>
							<td scope="row">${article.id}</td>
							<td>${article.label }</td>
							<td>${article.quantity}</td>
							<td><input type="number" id="article${article.id}" value="0" name="quantity" min="0" onchange="setState()" max="${article.quantity}"></td>
							<td><span class="glyphicon glyphicon-remove"></span></td>
						</tr>							
					</c:forEach>
				</tbody>
			</table>
			
			<form id="order${sessionScope.currentOrder.id }" action="${pageContext.request.contextPath}/employee/BonLivraison" method="POST">
				<input type="hidden" name="id_order" value="${sessionScope.currentOrder.id }"/>
			</form>
			<button class="btn btn-default" onclick="getDeliveryNote('order${sessionScope.currentOrder.id }')">Créer Bon de Livraison</button>
			
			<form action="${pageContext.request.contextPath}/employee/Commande" method="POST">
				<input type="submit" value="Commande Suivante"/>
			</form>
		</div>
	</div>
	
	
	



<c:import url="/WEB-INF/fragments/footer.jsp"/>