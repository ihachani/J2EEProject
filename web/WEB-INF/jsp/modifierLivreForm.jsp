<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="./assets/img/book.png">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://lipis.github.io/bootstrap-social/bootstrap-social.css" rel="stylesheet">
    <link href="https://cdn.rawgit.com/ihachani/J2EEProject/master/web/WEB-INF/web/css/custom.css" rel="stylesheet">
    <script src="http://airve.github.io/js/response/response.min.js"></script>
    <title>Read it</title>
</head>
<body class="background-main">
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
    <div class="container">
        <form:form  action="#" method="post">            
            <div class="form-group">
                <label class="sr-only" for="isbn">langue</label>
                <label for="isbn">langue</label>
                <form:input path="langue" type="text" placeholder="Name" required="" id="titre" class="form-control" />
            </div>
            
            <div class="form-group">
                <label class="sr-only" for="isbn">titre</label>
                <label for="isbn">titre</label>
                <form:input path="titre" type="text" placeholder="Name" required="" id="titre" class="form-control" />
            </div>
            
            <div class="form-group">
                <label class="sr-only" for="isbn">description</label>
                <label for="isbn">description</label>
                <form:input path="description" type="text" placeholder="Name" required="" id="titre" class="form-control" />
            </div>
            
            <div class="form-group" >
                <label for="category">category</label>
                <br>
                <select name="categoryID" class="selectpicker"  id="categoryID" data-style="btn-primary">
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.id}">${category.titre}</option>
                    </c:forEach>
                    
                </select>
            </div>
                
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </form:form>
        
    </div>

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>