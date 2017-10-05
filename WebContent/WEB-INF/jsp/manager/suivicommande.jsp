<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

<!-- debut section -->
	<section id="page_suivicommande" class="section">
		<div class="container">
			
			<div class="row">
				<div class="col-separator col-xs-4 col-sm-4">
					<h3>Suivi Equipe</h3>
					
					<div class="panel-body">
							<table class="table table-hover">
									<thead>
										<tr>
											<th>Employé</th>
											<th>Commandes traitées</th>
										</tr>
									</thead>
								<tbody>
									<c:forEach var="employee" items="${employeeList}">
										<tr>
												<td scope="row">${employeeList.lastName} ${employeeList.firstName}</td>
												<td>${employeeList.resultat}</td>
										</tr>	
									</c:forEach>
								</tbody>
							</table>
						</div>
						
				</div><!-- fin -
			</div><!-- fin row -->
			
			<div class="row">
				<div class="col-separator col-xs-7 col-sm-7">
					<h3>Commande à traiter</h3>
					
					<div class="panel-body">
							<table class="table table-hover">
									<thead>
										<tr>
											<th>Commande</th>
											<th>Client</th>
											<th>Date</th>
											<th>Employé</th>
											<th>Etat</th>
										</tr>
									</thead>
								<tbody>
									<c:forEach var="commande" items="${orderList}">
										<tr>
												<td scope="row">${orderList.id}</td>
												<td>${orderList.customer}</td>
												<td>${orderList.orderDate}</td>
												<td>${orderList.employee}</td>
												<td>${orderList.state.toString()}</td>
										</tr>	
									</c:forEach>
								</tbody>
							</table>
						</div>
				</div><!-- fin -
			</div><!-- fin row -->
			
		</div><!-- fin container --> 
	</section><!-- fin section -->



<c:import url="/WEB-INF/fragments/footer.jsp"/>