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
    <div class="container pagination-custom">
        <!--search group-->
        <div class="input-group input-group-lg col-xs-12 col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 bookpage-search">
            <input type="text" class="form-control">
            <div class="input-group-btn">
                <button class="btn btn-primary" type="button"><span class=" glyphicon glyphicon-search"></span></button>
                <button class="btn btn-primary" type="button"><span class=" glyphicon glyphicon-tags"></span></button>
            </div>
            <!-- /input-group -->
        </div>
        <!--search ends-->
        <div class="row">
            <!-- Aside Categories-->
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
                                <c:forEach var="category" items="${categories}">
                                    <li class="list-group-item"><span class="glyphicon glyphicon-pencil text-primary"></span>
                                        <a href="${pageContext.request.contextPath}/recherche-livre?bookTitle=<c:out value="${category.titre}" />"><c:out value="${category.titre}" /> </a>
                                    </li>
                                </c:forEach>
                                
                            </ul>
                        </div>
                    </div>

                </div>
            </aside>

            <div class="col-sm-9 col-md-9 col-xs-12 ">
                <div class="row">

                    
                    <c:forEach var="book" items="${livres}">
                         
                        <div class="book-thumb col-xs-8 col-xs-offset-2 col-sm-4 col-sm-offset-0 col-md-4">
                            <div class="thumbnail" style="height: 210px;display:block;">
                                <div class="captiond">
                                    <h4><c:out value="${book.titre}" /></h4>
                                    <p class="small">
                                        <c:if test="${not empty sessionScope.user}">
                                            <c:if test="${sessionScope.user.state == 1}">
                                                <a href="${pageContext.request.contextPath}/supprimer-livre?isbn=<c:out value="${book.isbn}" />">[delete]</a> <a href="${pageContext.request.contextPath}/modifier-livre?isbn=<c:out value="${book.isbn}" />">[modify]</a>
                                            </c:if>
                                        </c:if>
                                    </p>
                                    <p class="small"><c:out value="${book.description}" /></p>
                                    <p class="text-uppercase">
                                        <a href="${pageContext.request.contextPath}/afficher-livre?isbn=${book.isbn}" class="label label-success" >Preview</a>
                                        <!--<a href="" class="label label-danger" >Details</a>-->
                                    </p>
                                </div>
                                <img class="img-responsive" src="http://www.appcoda.com/swift/startup/common-files/icons/Book@2x.png" alt="book">
                            </div>
                        </div>
                    </c:forEach>


                </div>

                <nav class=" center-block text-center">
                    <ul class="pagination">
                        <li class=""><a href="${paginationLink}&page=1"> <span aria-hidden="true">&laquo;</span></a></li>
                        <c:forEach var="i" begin="1" end="${nbPages}">
                            <li class="<c:if test="${i == currentPage}">active</c:if>"><a href="${paginationLink}&page=<c:out value="${i}" />"> <span><c:out value="${i}" /></span></a></li>
                        </c:forEach>
                        <li><a href="${paginationLink}&page=${nbPages}"><span aria-hidden="true">&raquo;</span></a></li>
                    </ul>
                </nav>

                <div class="visible-xs pagination-custom">

                </div>
            </div>
        </div>
    </div>
    <!--nav start -->
    <nav id="main-navbar" role="navigation" class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="./index.html" class="navbar-brand"><span class=" glyphicon glyphicon-book"></span> Read it</a>
            </div>
            <div class="navbar-collapse collapse" id="navbar">
                <ul class="nav navbar-nav">
                    <li ><a href="./index.html">Home <span class="sr-only">(current)</span></a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="#contact">Contact</a></li>
                    <li class="dropdown">
                        <a aria-expanded="false" role="button" data-toggle="dropdown" class="dropdown-toggle" href="#">Dropdown <span class="caret"></span></a>
                        <ul role="menu" class="dropdown-menu">
                            <li><a href="#">Action</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li class="divider"></li>
                            <li class="dropdown-header">Nav header</li>
                            <li><a href="#">Separated link</a></li>
                            <li><a href="#">One more separated link</a></li>
                        </ul>
                    </li>
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
    </footer>
    -->
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
