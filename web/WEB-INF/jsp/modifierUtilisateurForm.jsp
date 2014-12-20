<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <title>Read it - Sign up</title>
</head>
<!-- We can add facebook google+ and twitter signups logins-->
<body class="background-site">
    
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
        <div class="container ">
            <div class="row">
                <div class="">
                    <form:form  action="#" method="post">
                        <div class="form-group">
                            <label class="sr-only" for="user">Username</label>
                            <label for="user">Username</label>
                            <form:input path="username" type="text" placeholder="Username" required="" id="user" class="form-control" />
                        </div>

                        <div class="form-group">
                            <label class="sr-only" for="pass">Password</label>
                            <label for="pass">Password</label>
                            <form:input type="password" placeholder="Password" required="" id="pass" class="form-control" path="password" />
                        </div>
                        interests<form:input type="text" path="interests" />
                        pays<form:input type="text" path="pays" />
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form:form>
                </div>
            </div>
        </div>
       
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</body>
</html>
        

