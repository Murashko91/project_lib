<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="by.murashko.sergey.entities.*"%>
<%@page import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<div class="container">
	<div class="row">
		<%@include file="jspf/reg_header.jspf"%></div>
		<hr style="margin:0; padding: 0;  ">
	<div class="row">
		<%@include file="jspf/letters.jspf"%>
	</div>
	<div class="row">
		<hr style="margin: 0; padding: 0; margin-left: 20px;">
		<div class="col-md-2">
			<%@include file="jspf/left_menu.jspf"%>
		</div>

		<div class="col-md-10">
			<br>

			<div class="row ">
				<div class="col-md-7 col-md-offset-2 ">


					<div class="panel  panel-info">
					<div class="panel-heading">
					Заполните форму для регистрации
					</div>
						<div class="form-group  panel-body ">
							${addUserError}


							<form:form method="POST" modelAttribute="user"
								action="registration" >
									



							<p>	<form:label path="name">
									<spring:message code="username" />
								</form:label></p>
								<form:input path="name" cssClass="form-control" />
								<form:errors path="name" cssClass="bg-danger small" />
								<p class="bg-danger small" style="margin: 0; padding: 0;">${addUserError}</p>

							<p>	<form:label path="password">
									<spring:message code="password" />
								</form:label></p>
								<form:password path="password" cssClass="form-control" />
								<form:errors path="password" cssClass="bg-danger small" />

							<p>	<form:label path="mail">
									<spring:message code="mail" />
								</form:label></p>
								<form:input path="mail" cssClass="form-control" />
								<form:errors path="mail" cssClass="bg-danger small" />



							<div class="panel-footer">
									<input type="submit" class="btn btn-info form-control"
										value="<spring:message code="regitration"/>">
								</div>


							</form:form>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	<%@include file="jspf/footer.jspf"%>
</div>
