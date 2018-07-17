<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/png" href="<c:url value="/resource/images/icons/favicon.ico"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/vendor/bootstrap/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resource/vendor/bootstrap/css/bootstrap-datepicker.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/util.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/css/main.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resource/vendor/jquery-ui/jquery-ui.css"/>"/>
</head>
<body>
<div class="limiter">
    <div class="container-login100" style="background-image: url('/resource/images/bg-01.jpg');">
        <div class="wrap-login100 p-l-110 p-r-110 p-t-62 p-b-33">
            <div class="login100-form validate-form flex-sb flex-w">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger w-full text-center">
                        ${error}
                    </div>
                </c:if>
                <c:if test="${not empty message}">
                    <div class="alert alert-info w-full text-center">
                        ${message}
                    </div>
                </c:if>
                <a href="<c:url value="/login"/>" class="txt2 bo1">
                    Sign in now
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>