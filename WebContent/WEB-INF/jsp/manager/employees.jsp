<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

<div class="container">
	<div class="row">
		<div class="col-sm-12 col-xs-12">
			<h1>Gestion des Préparateurs de Commandes</h1>
			
			<form class="connexion" action="${pageContext.request.contextPath}/manager/EditEmployee" method="post">
	   			 <div class="add-task-row">
		   			<input type="submit" class="btn btn-md btn-primary" name="add" value="Créer un nouvel employé">		   			
		   		</div>
			</form>
		</div> 
	</div>
	<div class="panel-body">		
		<c:if test="${listEmployees.size() < 1}">
			<div class="ligne-employe">
				<span>Il n'y a aucun employé dans la base de données pour le moment.</span>
			</div>
		</c:if>
	
		<c:if test="${listEmployees.size() > 0}">
			<div class="bloc-employe">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Numéro</th>
							<th>Nom et Prénom</th>
							<th>Poste</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listEmployees}" var="employee">														
							<tr>
							<form action="${pageContext.request.contextPath}/manager/EditEmployee" method="post">
								<input type="hidden" name="id_employee" value="${employee.id}"/>
								<td scope="row">${employee.id}</td>
								<td>${employee.lastName} ${employee.firstName}</td>
								<td>${employee.profile}</td>
								<td><input type="submit" class="btn btn-md btn-primary" name="modify" value="Modifier"/></td>
							</form>
							<form id="formSuppression${employee.id}" action="${pageContext.request.contextPath}/manager/DeleteEmployee" method="POST">
								<input type="hidden" name="id_employee" value="${employee.id}"/>
							</form>
							<td><button class="btn btn-md btn-danger" name="delete" onclick="confirmEmployeeSuppression('formSuppression${employee.id}')" value="">Supprimer</button></td>
							</tr>							
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
</div>
<c:import url="/WEB-INF/fragments/footer.jsp" />
