<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

	<section class="section">
		<div class="container">
			<div class="row">
				<div class="col-sm-6 col-xs-6">
					<h3>Importation des commandes</h3>
						<div class="panel-body">
							<form action="${pageContext.request.contextPath}/manager/import" method="post" enctype="multipart/form-data" acceptcharset="UTF-8">
								<fieldset>
							   		<label for="file">Fichier CSV à importer : </label>
							   		<input type="file" id="file" name="file" class="form-control" accept=".csv"></br>
						  			<input type="submit" class="btn btn-md btn-primary" value="Importer les commandes"></br></br>
								</fieldset>
							</form>
							<div>${import_message}</div>
						</div>
				</div>
			</div><!-- fin row -->	
		</div><!-- fin container --> 
	</section><!-- fin section -->
<c:import url="/WEB-INF/fragments/footer.jsp"/>