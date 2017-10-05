<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

<!-- debut section -->
	<section id="page_employees" class="section">
		<div class="container">			
			<div class="row">
				<div class="col-sm-8 col-xs-8">
					<h3>Ajout/Modification d'un employé</h3>
					
					<div class="panel-body">
				 		<form id="editEmploye" class="editEmploye" action="${pageContext.request.contextPath}/manager/SaveEmployee" method="post"> 				 		
				 				<input type="hidden" id="id_employee" name="id_employee" value="${currentEmployee.id}"/><br>
   								<label id="login" >Identifiant :</label>
     							<input type="text" id="txtboxLogin" name="txtboxLogin" size="100" class="form-control" value="${currentEmployee.login}" required /><br>
   								<label id="password" >Mot de passe :</label> 
   								<input type="password" id="txtboxPassword" name="txtboxPassword" size="100" class="form-control" value="${currentEmployee.password}" required/><br>    
   								<label id="lastName" >Nom :</label> 
   								<input type="text" id="txtboxLastName" name="txtboxLastName" size="100" class="form-control" value="${currentEmployee.lastName}" required/><br>
   								<label id="firstName" >Prénom :</label> 
   								<input type="text" id="txtboxFirstName" name="txtboxFirstName" size="100" class="form-control" value="${currentEmployee.firstName}" required/><br>
   								<label id="profile" >Poste :</label><br>
   								<select class="form-control" name="selectProfile" required>
								    <option value="manager">Manager</option>
								    <option value="shipping_clerk">Préparateur de Commandes</option>
								</select><br>   								   								
								<input type="submit" class="btn btn-md btn-primary" id="save" name="save" value="Enregistrer"/> 
								<input type="submit" class="btn btn-md btn-primary" id="cancel" name="cancel" value="Annuler"/><br>
							</fieldset> 
    					</form>
  				 	</div><!-- fin panel body -->					
				</div>
			</div><!-- fin row -->	
		</div><!-- fin container --> 
	</section><!-- fin section -->

<c:import url="/WEB-INF/fragments/footer.jsp"/>
