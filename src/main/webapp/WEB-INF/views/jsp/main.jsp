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
		<hr style="margin:0; padding: 0;  ">
	<div  class="row">	<%@include file="jspf/letters.jspf"%>
	</div>
	<div class="row">
	<hr style="margin:0; padding: 0; margin-left: 20px; ">
		<div class="col-md-2">
			<%@include file="jspf/left_menu.jspf"%>
		</div>

		<div class="col-md-10">
<br>
	<label class="bg-success text-success"><big>${regStatus}</big></label>
	
		</div>
	</div>
	<%@include file="jspf/footer.jspf"%>
</div>


