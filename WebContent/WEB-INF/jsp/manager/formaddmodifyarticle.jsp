<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
<c:import url="/WEB-INF/fragments/menumanager.jsp" />

<!-- debut section -->
	<section id="page_articles" class="section">
		<div class="container">
			
			<div class="row">
				<div class="col-sm-8 col-xs-8">
					<h3>Ajout/Modification d'un article</h3>
					
					<div class="panel-body">
				 		<form id="editArticle" action="${pageContext.request.contextPath}/manager/SaveArticle" method="post"> 
				 		
				 			<fieldset class="editArticle" id="write_article" name="write_article" title="Saisie Article">
   								<input type="hidden" id="id_article" name="id_article" value="${articleCurrent.id}"/><br>
   								<label id="label" >Designation :</label> 
   								<span class="message">${erreurs['label']}</span>
   								<input type="text" id="txtboxLabel" name="txtboxLabel" size="100"  class="form-control"  value="${articleCurrent.label}" required /><br>
   								<label id="weight" >Poids (g) :</label> 
   								<span class="message">${erreurs['weight']}</span>
   								<input type="text" id="txtboxWeight" name="txtboxWeight" size="100" class="form-control"  value="${articleCurrent.weight}" required/><br>
   								<label id="description" >Description :</label> 
   								<span class="message">${erreurs['description']}</span>
   								<textarea id="txtboxDescription" name="txtboxDescription" class="form-control"  rows="10" size="255" value="${articleCurrent.description}" required>${articleCurrent.description}</textarea><br>
								<input type="submit" class="btn btn-md btn-primary" id="save" name="save" value="Enregistrer"/> 
								<a class="btn btn-md btn-primary" href="${pageContext.request.contextPath}/manager/listarticles">Annuler</a><br>
							</fieldset> 
    					</form>
  				 	</div><!-- fin panel body -->	
				
				</div>
			</div><!-- fin row -->	
		</div><!-- fin container --> 
	</section><!-- fin section -->

<c:import url="/WEB-INF/fragments/footer.jsp"/>