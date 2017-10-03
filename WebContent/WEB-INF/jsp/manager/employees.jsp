<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
<c:import url="/WEB-INF/fragments/menu.jsp" />

<div class="container">
	<h1>Gestion des Préparateurs de Commandes</h1>

	<c:if test="${employees.size() < 1}">
		<div class="ligne-employe">
			<span>Il n'y a aucun employé dans la base de données pour le moment.</span>
		</div>
	</c:if>

	<c:if test="${employees.size() > 0}">
		<div class="bloc-employe">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Numéro</th>
						<th>Nom et Prénom</th>
						<th>Poste</th>
						<th><input type="submit" name="ajouter" value="Ajouter"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${employees}" var="employee">
						<tr>
							<td scope="row">${employee.id}</td>
							<td>${employee.lastName} ${employee.firstName}</td>
							<td>${employee.profile}</td>
							<td><input type="submit" name="modifier" value="Modifier"></td>
							<td><input type="submit" name="supprimer" value="Supprimer"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</div>
<c:import url="/WEB-INF/fragments/footer.jsp" />
