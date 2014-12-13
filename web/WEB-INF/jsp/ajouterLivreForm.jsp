<%-- 
    Document   : ajouterLivreForm
    Created on : 1 déc. 2014, 14:41:56
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
        <c:if test="${not empty errorMsg}">
            ${errorMsg}
        </c:if>
        <c:if test="${not empty notificationMsg}">
            ${notificationMsg}
        </c:if>
        <c:if test="${not empty validationMsg}">
            ${validationMsg}
        </c:if>
        <h1>Hello World!</h1>
    </body>
</html>
