<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <jsp:include page="include/client_css.jsp"/>

    <title>Client page</title>
</head>
<body class="bg-light">
    <jsp:include page="include/client_header.jsp"/>
<div class="d-flex">
    <jsp:include page="include/client_menu.jsp"/>

    <div id="main-block" class="content p-4">
        <c:choose>
            <c:when test="${not empty error}">
                <div class="alert alert-danger">
                    ${error}
                </div>
            </c:when>
            <c:otherwise>
                <h2 class="mb-4">Blank/Starter</h2>

                <div class="card mb-4">
                    <div class="card-body">
                        This is a blank page you can use as a starting point.
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<jsp:include page="include/client_js.jsp"/>
</body>
</html>
