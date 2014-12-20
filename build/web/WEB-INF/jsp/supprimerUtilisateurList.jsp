<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
   
            <aside class="col-sm-3 col-md-3 hidden-xs">
                <div class="panel-group" id="accordion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne"><span class="glyphicon glyphicon-folder-close">
                    </span>Content</a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in">
                            <ul class="list-group">
                                <c:forEach var="user" items="${users}">
                                    <li class="list-group-item"><span class="glyphicon glyphicon-pencil text-primary"></span>
                                        <c:out value="${user.username}" /><a href="${pageContext.request.contextPath}/supprimer-utilisateur?userID=<c:out value="${user.id}" />">[delete]</a>
                                    </li>
                                </c:forEach>
                                
                            </ul>
                        </div>
                    </div>

                </div>
            </aside>

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
