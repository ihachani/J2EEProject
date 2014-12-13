<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>Hello World</title>
</head>
<body>
    <c:if test="${not empty errorMsg}">
        ${errorMsg}
    </c:if>
    <c:if test="${not empty notificationMsg}">
        ${notificationMsg}
    </c:if>
    ${notificationMsg}
    <c:if test="${not empty validationMsg}">
        ${validationMsg}
    </c:if>
    
   <h2>indexHELLO</h2>
</body>
</html>