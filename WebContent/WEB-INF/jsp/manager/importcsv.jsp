<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

	<section class="section">
		<div class="container">
				<div class="col-sm-12 col-xs-12">
					<h3>Importation des commandes</h3>
					<form action="${pageContext.request.contextPath}/manager/import" method="post" enctype="multipart/form-data" acceptcharset="UTF-8">
						<fieldset>
							<div>
							   <label for="file">Fichier CSV à importer : </label>
							   <input type="file" id="file" name="file" accept=".csv">
							</div>
							<div>
						  	<input type="submit" class="button" value="Importer les commandes">
						</div>
						</fieldset>
					</form>
					<div>${import_message}</div>
				</div>
		</div>
	</section>
<c:import url="/WEB-INF/fragments/footer.jsp"/>