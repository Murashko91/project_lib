
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="jspf/header.jspf"%>
<%@include file="jspf/letters.jspf"%>
<%@include file="jspf/left_menu.jspf"%>


<div class="book_list">


	<h5 style="text-align: left; margin-top: 20px;">Найдено книг:
		${fn:length(bookList)}</h5>

	<c:forEach items="${bookList}" var="book">

		<div class="book_info">
			<div class="book_title">
				<p>${book.name}</p>
			</div>
			<div class="book_image">
				<a href="contentController/${book.id}.pdf"><img
					src="imageController/${book.id}.jpg"
					height="250" width="190" alt="Обложка" /></a> 
			</div>
			<div class="book_details">
				<br> <strong>ISBN:</strong> ${book.name} <br> <strong>Издательство:</strong>
				${book.name} <br> <strong>Количество страниц:</strong>
				${book.name} <br> <strong>Год издания:</strong> ${book.name} <br>
				<strong>Автор:</strong> ${book.name}
				<p style="margin: 10px;">
					<a
						href="contentController/${book.id}.pdf">Читать</a> 
				</p>
			</div>
		</div>

	</c:forEach>


</div>
<%@include file="jspf/footer.jspf"%>
