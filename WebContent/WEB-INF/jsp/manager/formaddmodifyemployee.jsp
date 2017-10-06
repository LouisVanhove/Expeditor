<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp" />
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

<!-- debut section -->
<section id="page_employees" class="section">
	<div class="container">
		<div class="row">
			<div class="col-sm-8 col-xs-8">
				<h3>Ajout/Modification d'un employé</h3>

				<div class="panel-body">
					<form id="editEmploye" class="editEmploye"
						action="${pageContext.request.contextPath}/manager/SaveEmployee" method="post">
						<fieldset id="write_employee" name="write_employee" value="Saisie Employé">
						    <input type="hidden" id="id_employee" name="id_employee" value="${requestScope.currentEmployee.id}"/><br>						
							<label id="login">Identifiant :</label> 
							<span class="message">${errors['login']}</span>
							<input type="text" id="txtboxLogin" name="txtboxLogin" size="100" class="form-control" value="${requestScope.currentEmployee.login}" required /><br>
							<label id="password">Mot de passe :</label> 
							<span class="message">${errors['password']}</span> 
							<input type="password" id="txtboxPassword" name="txtboxPassword" size="100" class="form-control" value="${requestScope.currentEmployee.password}" required /><br>
							<label id="passwordConfirm">Confirmer le mot de passe :</label> 
							<span class="message">${errors['passwordConfirm']}</span> 
							<input type="password" id="txtboxPasswordConfirm" name="txtboxPasswordConfirm" size="100" class="form-control" value="${requestScope.currentEmployee.password}" required />
							<input type="hidden" id="comparePassword" name="comparePassword" size="100" value="${requestScope.currentEmployee.password}" />
							<label id="lastName">Nom :</label> 
							<span class="message">${errors['lastName']}</span>
							<input type="text" id="txtboxLastName" name="txtboxLastName" size="100" class="form-control" value="${requestScope.currentEmployee.lastName}" required /><br>
							<label id="firstName">Prénom :</label> 
							<span class="message">${errors['firstName']}</span>
							<input type="text" id="txtboxFirstName" name="txtboxFirstName" size="100" class="form-control" value="${requestScope.currentEmployee.firstName}" required /><br>
							<label id="profile">Poste :</label><br> 
							<select class="form-control" name="selectProfile" required>
								<c:forEach items="${sessionScope.profiles}" var="profile">
									<option value="${profile.getId()}"
										<c:if test="${profile.equals(currentEmployee.profile)}"> selected</c:if>>
										${profile.toString()}</option>
								</c:forEach>
							</select><br> 
							<input type="submit" class="btn btn-md btn-primary" id="save" name="save" value="Enregistrer" /> 
							<a href="${pageContext.request.contextPath}/manager/ListEmployees" class="btn btn-md btn-primary">Annuler</a><br>
						</fieldset>
					</form>
				</div>
				<!-- fin panel body -->
			</div>
		</div>
		<!-- fin row -->
	</div>
	<!-- fin container -->
</section>
<!-- fin section -->

<c:import url="/WEB-INF/fragments/footer.jsp" />
