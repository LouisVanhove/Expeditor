<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/WEB-INF/fragments/header.jsp"/>
	<br>
	<br>${sessionScope.currentOrder.id }
	<br>${sessionScope.currentOrder.customer }
	<br>${sessionScope.currentOrder.orderDate }
	<br>${sessionScope.currentOrder.employee.lastName }
	<br>${sessionScope.currentOrder.employee.firstName }
	<br>${sessionScope.currentOrder.customer.name }
	<br>${sessionScope.currentOrder.state}
	<br>${sessionScope.currentOrder.archived }
	<c:forEach var="article" items="${sessionScope.currentOrder.listArticles}">
		${article.label}<br>
	</c:forEach>



<c:import url="/WEB-INF/fragments/footer.jsp"/>