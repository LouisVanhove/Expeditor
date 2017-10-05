<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp" />
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

<div class="container">
	<div class="row">
		<div class="col-sm-12 col-xs-12">
			<h1>Gestion Employés</h1>

			<c:if test="${listEmployees.size() < 1}">
				<div class="ligne-employe">
					<span>Il n'y a aucun employé dans la base de données pour le
						moment.</span>
				</div>
			</c:if>

			<c:if test="${listEmployees.size() > 0}">
				<div class="panel-body">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Numéro</th>
								<th>Nom et Prénom</th>
								<th>Poste</th>
								<th></th>
								<form class="connexion"	action="${pageContext.request.contextPath}/manager/EditEmployee" method="post">
									<th><input type="submit" class="btn btn-md btn-primary"	name="add" value="Créer un nouvel employé"></th>
								</form>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listEmployees}" var="employee">
								<tr>
									<form action="${pageContext.request.contextPath}/manager/EditEmployee" method="post">
										<input type="hidden" name="id_employee" value="${employee.id}" />
										<td scope="row">${employee.id}</td>
										<td>${employee.lastName} ${employee.firstName}</td>
										<td>${employee.profile.toString()}</td>
										<td><input type="submit" class="btn btn-md btn-primary" name="modify" value="Modifier" /></td>
									</form>
									<form id="formSuppression${employee.id}" action="${pageContext.request.contextPath}/manager/DeleteEmployee" method="POST">
										<input type="hidden" name="id_employee" value="${employee.id}" />
										<input type="hidden" name="delete" value="delete"/>										
									</form>
									<td><button class="btn btn-md btn-danger" onclick="confirmEmployeeSuppression('formSuppression${employee.id}')"
											value="">Supprimer</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/fragments/footer.jsp" />
