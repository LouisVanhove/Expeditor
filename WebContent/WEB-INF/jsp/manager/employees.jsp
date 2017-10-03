<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
	<h1>Gestion des Préparateurs de Commandes</h1>

	<c:if test="${employees.size() > 0}">

		<div class="bloc-employe">
			<div class="title-employe row">
				<span>Numéro</span>
				<span>Nom et Prénom</span>
				<span>Poste</span>
				<input type="submit" name="ajouter" value="Ajouter">
			</div>
			<c:forEach items="${employees}" var="employe">
				<%-- <jsp:include page="tabEmploye.jsp"></jsp:include> --%>
				<div class="tab-employe row">
					<span>${employe.id}</span>
					<span>${employe.lastName} ${employe.firstName}</span>
					<span>${employe.profile}</span>
					<input type="submit" name="modifier" value="Modifier">
					<input type="submit" name="supprimer" value="Supprimer">
				</div>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${employees.size() < 1}">
		<div class="ligne-employe">
			<span>Il n'y a aucun employé dans la base de données pour le
				moment.</span>
		</div>
	</c:if>
<c:import url="/WEB-INF/fragments/footer.jsp"/>
