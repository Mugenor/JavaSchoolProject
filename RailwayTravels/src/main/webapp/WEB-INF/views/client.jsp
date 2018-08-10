<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="username" content="<security:authentication property="principal.username"/>">
    <base href="${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="resource/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="resource/vendor/bootadmin/css/bootadmin.min.css">
    <link rel="stylesheet" href="resource/vendor/jquery-ui/jquery-ui.css">
    <link rel="stylesheet"
          href="resource/vendor/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>


    <title>Client page</title>
</head>
<body>
<app-root></app-root>
<script type="text/javascript" src="resource/client/runtime.js"></script>
<script type="text/javascript" src="resource/client/polyfills.js"></script>
<script type="text/javascript" src="resource/client/styles.js"></script>
<script type="text/javascript" src="resource/client/vendor.js"></script>
<script type="text/javascript" src="resource/client/main.js"></script>
<script src="resource/vendor/jquery-ui/jquery-ui.js"></script>
<script src="resource/vendor/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
</body>
</body>
</html>
