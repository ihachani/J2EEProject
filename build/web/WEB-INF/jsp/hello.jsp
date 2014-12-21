<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <div class="warper container-fluid center-container">
            <div class="vcentre">
                <form class="form-signin" role="form" action="${pageContext.request.contextPath}/recherche-livre" >
                    <div class="input-group input-group-lg index-search col-xs-12 col-sm-8 col-sm-offset-2 ">
                        <input type="text" class="form-control" name="bookTitle" >
                        <div class="input-group-btn">
                            <button class="btn btn-primary" type="submit"><span class=" glyphicon glyphicon-search"></span></button>
                            <!-- <button class="btn btn-primary" type="button"><span class=" glyphicon glyphicon-tags"></span></button>-->
                        </div>
                        <!-- /input-group -->
                    </div>
                   <!--  <button class="btn btn-lg btn-primary explore-btn" href="booklist.html">Explore!</button>-->
                </form>
            </div>

        <nav id="main-navbar" role="navigation" class="navbar navbar-default navbar-fixed-top" style="-webkit-box-shadow: none; -moz-box-shadow: none; box-shadow: none;">
            <div class="container">
                <div class="navbar-header">
                    <button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a href="${pageContext.request.contextPath}/" class="navbar-brand"><span class=" glyphicon glyphicon-book"></span> Read it</a>
                </div>
                <div class="navbar-collapse collapse" id="navbar">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="${pageContext.request.contextPath}/">Home <span class="sr-only">(current)</span></a></li>
                        <!--<li><a href="#about">About</a></li>
                        <li><a href="#contact">Contact</a></li>-->
                        <li class="dropdown">
                            <a aria-expanded="false" role="button" data-toggle="dropdown" class="dropdown-toggle" href="#">Actions<span class="caret"></span></a>
                            <ul role="menu" class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/recherche-livre">View All</a></li>
                                <c:if test="${not empty sessionScope.user}">
                                    <li class="divider"></li>
                                    <li><a href="${pageContext.request.contextPath}/ajouter-demande-livre">add book request</a></li>
                                    <li><a href="${pageContext.request.contextPath}/demande-livre-list">view book requests</a></li>
                                    <li><a href="${pageContext.request.contextPath}/ajouter-livre">add a book</a></li>
                                    <c:if test="${sessionScope.user.state == 1}">
                                        <li class="divider"></li>
                                         <!--<li class="dropdown-header">Nav header</li>-->
                                        <li><a href="${pageContext.request.contextPath}/ajouter-category">add category</a></li>
                                        <li><a href="${pageContext.request.contextPath}/supprimer-category-list">view categories list</a></li>
                                        <li><a href="${pageContext.request.contextPath}/accepter-livre-list">view added books list</a></li>
                                        <li><a href="${pageContext.request.contextPath}/supprimer-utilisateur">view users list</a></li>
                                    </c:if>
                                </c:if>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <c:if test="${empty sessionScope.user}">
                            <li><a href="${pageContext.request.contextPath}/register-utilisateur">Sign-up</a></li>
                        </c:if>
                        <!--Signin Dropdown-->
                        <li class="dropdown">
                            <a aria-expanded="false" role="button" data-toggle="dropdown" class="dropdown-toggle" href="#">Login<span class="caret"></span></a>
                            <ul role="menu" class="dropdown-menu">
                                <c:if test="${not empty sessionScope.user}">
                                    <li><a href="${pageContext.request.contextPath}/modifier-utilisateur">update informations</a></li>
                                    <li><a href="${pageContext.request.contextPath}/deconnect-utilisateur">deconnection</a></li>
                                </c:if>
                                <c:if test="${empty sessionScope.user}">
                                    <form class="form-signin" role="form" action="${pageContext.request.contextPath}/login-utilisateur" method="post">
                                        <li>
                                            <label for="inputUusername" class="sr-only">username</label>
                                            <input name="username" style=" background-repeat: no-repeat; background-attachment: scroll; background-position: right center; cursor: auto;" id="inputEmail" class="form-control" placeholder="username" required="" autofocus="" type="text">
                                        </li>
                                        <li>
                                            <label for="inputPassword" class="sr-only">password</label>
                                            <input name="password" style=" background-repeat: no-repeat; background-attachment: scroll; background-position: right center; cursor: auto;" id="inputPassword" class="form-control" placeholder="password" required="" type="password">
                                        </li>
                                    <!-- <li>
                                            <div class="checkbox">
                                                <label>
                                                    <input value="remember-me" type="checkbox"> Remember me
                                                </label>
                                            </div>
                                        </li>-->
                                        <li>
                                            <button class="btn btn-sm btn-primary btn-block" type="submit">Sign in</button>
                                        </li>
                                        <!--<li>
                                            <a href="#" class="small"> Forgot password</a>
                                        </li> -->
                                    </form>
                                </c:if>
                                
                            </ul>
                        </li>
                        <!--Sign-in Dropdown -->
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </nav>
        <!-- nav ends -->



    </div>
    <!-- for xs-devices -->


    <!-- javascript -->

    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script>
        jQuery.fn.verticalAlign = function ()
        {
            return this
                    .css("margin-top",($(this).parent()
                            .height() - $(this).height())/2 + 'px' )
        };
        $(function () {
            //$('.center-container').height($(document).height() - 70);
            $('.vcentre').verticalAlign();

        });
    </script>
</body>
</html>