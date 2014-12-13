<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : searchResult
    Created on : 30 nov. 2014, 16:30:52
    Author     : faiez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        nbPages${nbPages}
        <br />
        <br />
    <c:forEach var="book" items="${livres}">
        <c:out value="${book.titre}" /> 
            <br />
    </c:forEach>
            <br />
    <c:forEach var="category" items="${categories}">
        <c:out value="${category.titre}" /> 
            <br />
    </c:forEach>
    </body>
</html>
