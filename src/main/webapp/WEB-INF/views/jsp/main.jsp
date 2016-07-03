<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="container">
	<div class="row">
		<%@include file="jspf/header.jspf"%></div>
	<div class="row">
		<%@include file="jspf/letters.jspf"%>
	</div>
	<div class="row">
		<hr style="margin: 0; padding: 0; margin-left: 20px;">
		<div class="col-md-2">
			<%@include file="jspf/left_menu.jspf"%>
		</div>

		<div class="col-md-10">
	
			<br> <label class="bg-success text-success"><big>${regStatus}</big></label>
			<label class="bg-success text-success"><big>${addMessage}</big></label>
		<label class="bg-danger "><big>${addMessageError}</big></label>
			<c:if test="${param.add_message != null}">

				<div class="panel  panel-info">
					<div class="panel-heading">${user.name}, заполнитеформу чтобы
						отправить письмо администратору.</div>
					<div class="form-group  panel-body ">
					


						<form:form method="POST" modelAttribute="message"
							action="addmessage">
							<input type="text" id="user_name" name="user_name"
								value="${user.name}" style="display: none;">
							<textarea  rows="10" name="message" id="message" class="form-control"
								placeholder="Введите сообщение..."></textarea>

						

							<div class="panel-footer">
								<input type="submit" class="btn btn-info form-control"
									value="Отправить">
							</div>


						</form:form>
					</div>
				</div>

			</c:if>

		</div>
	</div>
	<%@include file="jspf/footer.jspf"%>
</div>


