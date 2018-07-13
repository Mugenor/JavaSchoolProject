<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>


    <jsp:include page="include/client_css.jsp"/>
    <link rel="stylesheet" href="/resource/vendor/jquery-ui/jquery-ui.css"/>
    <link rel="stylesheet" href="/resource/css/validation-feedback.css"/>
    <link rel="stylesheet"
          href="/resource/vendor/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>

    <title>Admin page</title>
</head>
<body class="bg-light" ng-app="adminApp">
<jsp:include page="include/header.jsp"/>
<div class="d-flex">
    <jsp:include page="include/admin_menu.jsp"/>
    <div id="main-block" class="content p-4" ng-view>
    </div>
</div>
<jsp:include page="include/admin_js.jsp"/>
<script src="/resource/admin/admin-app.js"></script>
<script src="/resource/admin/service/departure-table-service.js"></script>
<script src="/resource/admin/service/departure-passengers-service.js"></script>
<script src="/resource/admin/service/station-service.js"></script>
<script src="/resource/admin/service/station-table-service.js"></script>
<script src="/resource/admin/directive/contained-in-validator.js"></script>
<script src="/resource/admin/controller/all-departures-controller.js"></script>
<script src="/resource/admin/controller/departure-controller.js"></script>
<script src="/resource/admin/controller/stations-controller.js"></script>
</body>
</html>
