<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="<c:url value="/resource/vendor/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resource/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resource/vendor/bootadmin/css/bootadmin.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resource/vendor/datatables/css/datatables.min.css"/>">

    <title>Client page</title>
</head>
<body class="bg-light">

<nav class="navbar navbar-expand navbar-dark bg-primary">
    <a class="sidebar-toggle mr-3" href="#"><i class="fa fa-bars"></i></a>
    <a class="navbar-brand" href="#">BootAdmin</a>

    <div class="navbar-collapse collapse">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a href="#" id="dd_user" class="nav-link dropdown-toggle" data-toggle="dropdown"><i
                        class="fa fa-user"></i> John Doe</a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dd_user">
                    <form method="POST" action="<c:url value="/logout"/>">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" class="dropdown-item" value="Logout"/>
                    </form>
                </div>
            </li>
        </ul>
    </div>
</nav>

<div class="d-flex">
    <div class="sidebar sidebar-dark bg-dark">
        <ul class="list-unstyled">
            <li>
                <a href="#sm_expand_1" data-toggle="collapse">
                    <i class="fa fa-fw fa-link"></i> Departures
                </a>
                <ul id="sm_expand_1" class="list-unstyled collapse">
                    <li id="all-departures"><a href="#all-departures">All departures</a></li>
                    <li><a href="#">Find departure</a></li>
                </ul>
            </li>
            <li id="all-stations"><a href="#all-stations"><i class="fa fa-fw fa-link"></i> Stations</a></li>
        </ul>
    </div>

    <div id="main-block" class="content p-4">
        <h2 class="mb-4">Blank/Starter</h2>

        <div class="card mb-4">
            <div class="card-body">
                This is a blank page you can use as a starting point.
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/resource/vendor/jquery/jquery-3.3.1.js"/>"></script>
<script src="<c:url value="/resource/vendor/bootstrap/js/popper.js"/>"></script>
<script src="<c:url value="/resource/vendor/bootstrap/js/bootstrap.bundle.js"/>"></script>
<script src="<c:url value="/resource/vendor/bootadmin/js/bootadmin.js"/>"></script>
<script src="<c:url value="/resource/vendor/datatables/js/datatables.js"/>"></script>
<script src="<c:url value="/resource/js/client-get-all-departures.js"/>"></script>
<script src="<c:url value="/resource/js/client-get-all-stations.js"/>"></script>

</body>
</html>
