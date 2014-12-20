<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
    <title>Read it - Book List</title>
</head>
<body class="background-site">
    <c:if test="${not empty errorMsg}">
        ${errorMsg}
    </c:if>
    <c:if test="${not empty notificationMsg}">
        ${notificationMsg}
    </c:if>
    <c:if test="${not empty validationMsg}">
        ${validationMsg}
    </c:if>
    <div class="container pagination-custom">
        <div class="row">
                                <div class="captiond">
                                    <h4><c:out value="${livre.titre}" /></h4>
                                    <p class="small">
                                        <c:if test="${not empty sessionScope.user}">
                                            <c:if test="${sessionScope.user.state == 1}">
                                                <a href="${pageContext.request.contextPath}/supprimer-livre?isbn=<c:out value="${livre.isbn}" />">[delete]</a> <a href="${pageContext.request.contextPath}/modifier-livre?isbn=<c:out value="${livre.isbn}" />">[modify]</a>
                                            </c:if>
                                        </c:if>
                                    </p>
                                    <p class="small"><c:out value="${livre.description}" /></p>
                                    <p class="text-uppercase">
                                        <a href="${pageContext.request.contextPath}/afficher-livre?isbn=${livre.isbn}" class="label label-success" >Preview</a>
                                        <!--<a href="" class="label label-danger" >Details</a>-->
                                    </p>
                                </div>
                                <img class="img-responsive" src="http://www.appcoda.com/swift/startup/common-files/icons/Book@2x.png" alt="book">
        </div>
    </div>
                                        
    <div id="collapseOne" class="panel-collapse collapse in">
            <ul class="list-group">
                <c:forEach var="note" items="${notes}">
                    <li class="list-group-item"><span class="glyphicon glyphicon-pencil text-primary"></span>
                        <c:out value="${note.rate}" /> | <c:out value="${note.review}" /><c:if test="${not empty sessionScope.user}"> <c:if test="${sessionScope.user.state == 1}"><a href="${pageContext.request.contextPath}/supprimer-note-livre?isbn=<c:out value="${livre.isbn}" />&noteID=<c:out value="${note.id}" />">[delete]</a></c:if></c:if>
                    </li>
                </c:forEach>

            </ul>
        </div>                                    
                                        
                                        <c:if test="${not empty sessionScope.user}">
    <div class="container">
        <form:form  action="${pageContext.request.contextPath}/noter-livre?isbn=${livre.isbn}" method="post">
            <div class="form-group">
                <label class="sr-only" for="name">rate</label>
                <label for="name">rate</label>
                <form:input path="rate" type="text" placeholder="rate" required="" id="titre" class="form-control" />
            </div>
            <div class="form-group">
                <label class="sr-only" for="name">review</label>
                <label for="name">review</label>
                <form:input path="review" type="text" placeholder="review" required="" id="titre" class="form-control" />
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </form:form>
        
    </div>
            
            </c:if>
    <!-- javascript -->
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script >
        $('.thumbnail').hover(
        function(){
        $(this).find('.caption').slideDown(250); //.fadeIn(250)
        },
        function(){
        $(this).find('.caption').slideUp(250); //.fadeOut(205)
        }
        );

    </script>
</body>
</html>
