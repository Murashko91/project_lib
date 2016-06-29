
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="jspf/admin/adminheader.jspf"%>

<c:if test="${param.add_book != null}">
	
<%@include file="jspf/admin/add_book.jspf"%>

</c:if>

<c:if test="${param.add_author != null}">
	
<%@include file="jspf/admin/add_author.jspf"%>

</c:if>
<c:if test="${param.add_genre != null}">
	
<%@include file="jspf/admin/add_genre.jspf"%>

</c:if>
<c:if test="${param.add_publisher != null}">
	
<%@include file="jspf/admin/add_publisher.jspf"%>

</c:if>


<c:if test="${param.remove_book != null}">
	
<%@include file="jspf/admin/remove_book.jspf"%>

</c:if>

 <%-- 
<c:if test="${param.add_user!= 0}">
	<%@include file="jspf/admin/add_user.jspf"%>
	</c:if>
	
	<c:if test="${param.edit_users!= 0}">
	<%@include file="jspf/edit_user.jspf"%>
	</c:if>
	
	<c:if test="${param.edit_book != 0}">
	<%@include file="jspf/letters.jspf"%>
	<%@include file="jspf/left_menu.jspf"%>
<%@include file="jspf/edit_books.jspf"%>
</c:if>
 --%> 
<%@include file="jspf/footer.jspf"%>