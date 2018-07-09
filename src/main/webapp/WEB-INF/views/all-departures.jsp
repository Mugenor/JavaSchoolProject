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
        <div class="container-fluid">
            <table id="departures" class="table table-striped table-bordered table-light">
                <thead>
                <tr>
                    <th>Station of departure</th>
                    <th>Arrival station</th>
                    <th>Time of departure</th>
                    <th>Arrival time</th>
                    <th>Number of available seats</th>
                    <th>Get ticket</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<jsp:include page="include/client_js.jsp"/>
<script src="<c:url value="/resource/js/client-get-all-departures.js"/>"></script>

</body>
</html>
