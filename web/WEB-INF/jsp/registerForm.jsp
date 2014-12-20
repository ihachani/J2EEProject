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
                        <div class="form-group">
                            <label class="sr-only" for="email">Email Adress</label>
                            <label for="email">Email Adress</label>
                            <form:input type="email" placeholder="Email" required="" id="email" class="form-control"  path="email" />
                        </div>
                        interests<form:input type="text" path="interests" />
                        pays<form:input type="text" path="pays" />
                        dayNaissance<form:input type="text" path="dayNaissance" />
                        monthNaissance<form:input type="text" path="monthNaissance" />
                        yearNaissance<form:input type="text" path="yearNaissance" />
            
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form:form>
                </div>
            </div>
        </div>
        <!--nav start-->
        <nav id="main-navbar" role="navigation" class="navbar navbar-default navbar-fixed-top">
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
                        <li ><a href="${pageContext.request.contextPath}/">Home <span class="sr-only">(current)</span></a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="active"><a href="./signup.html">Sign-up</a></li>
                        <!--Signin Dropdown-->
                        <li class="dropdown">
                            <a aria-expanded="false" role="button" data-toggle="dropdown" class="dropdown-toggle" href="#">Login<span class="caret"></span></a>
                            <ul role="menu" class="dropdown-menu">
                                <form class="form-signin" role="form">
                                    <li>
                                        <label for="inputEmail" class="sr-only">Email address</label>
                                        <input style=" background-repeat: no-repeat; background-attachment: scroll; background-position: right center; cursor: auto;" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus="" type="email">
                                    </li>
                                    <li>
                                        <label for="inputPassword" class="sr-only">Password</label>
                                        <input style=" background-repeat: no-repeat; background-attachment: scroll; background-position: right center; cursor: auto;" id="inputPassword" class="form-control" placeholder="Password" required="" type="password">
                                    </li>
                                    <li>
                                        <div class="checkbox">
                                            <label>
                                                <input value="remember-me" type="checkbox"> Remember me
                                            </label>
                                        </div>
                                    </li>
                                    <li>
                                        <a class="btn btn-sm btn-primary btn-block" type="submit">Sign in</a>
                                    </li>
                                    <li>
                                        <a href="#" class="small"> Forgot password</a>
                                    </li>
                                </form>
                            </ul>
                        </li>
                        <!--Sign-in Dropdown -->
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </nav>
        <!-- nav ends -->
        <!-- Footer-->
        <div class="navbar navbar-default navbar-fixed-bottom">
            <div class="container">
                <p class="navbar-text pull-left">© 2014 - Site Built By Mr. M.
                    <a href="http://tinyurl.com/tbvalid" target="_blank" >HTML 5 Validation</a>
                </p>

                <a class="navbar-btn btn-social-icon btn-twitter pull-right">
                    <i class="fa fa-twitter"></i>
                </a>
                <a class="navbar-btn btn-social-icon btn-facebook pull-right">
                    <i class="fa fa-facebook"></i>
                </a>
                <a class="navbar-btn btn-social-icon btn-google-plus pull-right">
                    <i class="fa fa-google-plus"></i>
                </a>
            </div>
        </div>
        <!--Footer Ends -->
<!--    <footer class="footer">
        <div class="footer-seperator">

        </div>
        <div class="container">
            <div class="row">
                <p class="small col-sm-8 col-xs-6 ">This website is neither copyrighted nor protected by any law for the moment.</p>
                <div class="col-sm-4 col-xs-6 text-right">
                    <a class="btn btn-social-icon btn-twitter">
                        <i class="fa fa-twitter"></i>
                    </a>
                    <a class="btn btn-social-icon btn-facebook">
                        <i class="fa fa-facebook"></i>
                    </a>
                    <a class="btn btn-social-icon btn-google-plus">
                        <i class="fa fa-google-plus"></i>
                    </a>
                </div>
            </div>
        </div>
    </footer>-->
    <!-- javascript -->
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</body>
</html>
        

