<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <base href="${pageContext.request.contextPath}/">

    <jsp:include page="include/client_css.jsp"/>

    <title>Client page</title>
</head>
<body class="bg-light">
<jsp:include page="include/header.jsp"/>
<div class="d-flex">
    <jsp:include page="include/client_menu.jsp"/>
    <app-root></app-root>

    <div id="main-block" class="content p-4">
        <c:choose>
            <c:when test="${not empty error}">
                <div class="alert alert-danger">
                        ${error}
                </div>
            </c:when>
            <c:otherwise>
                <h3>Welcome!</h3>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<jsp:include page="include/client_js.jsp"/>
<script type="text/javascript" src="resource/client/runtime.js"></script>
<script type="text/javascript" src="resource/client/polyfills.js"></script>
<script type="text/javascript" src="resource/client/styles.js"></script>
<script type="text/javascript" src="resource/client/vendor.js"></script>
<script type="text/javascript" src="resource/client/main.js"></script>
</body>
</body>
</html>
