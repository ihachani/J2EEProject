<%-- 
    Document   : registerForm
    Created on : 30 nov. 2014, 20:24:11
    Author     : faiez
--%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
        <h1>registerForm</h1>
        <form:form  action="#" method="post">
            <p>Id: <form:input type="text" path="username" /></p>
            <p>Id: <form:input type="password" path="password" /></p>
            <p>Id: <form:input type="email" path="email" /></p>
            <p><input type="submit" value="Submit" /> 
            <input type="reset" value="Reset" /></p>
        </form:form>
    </body>
</html>
