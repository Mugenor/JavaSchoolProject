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
<jsp:include page="include/header.jsp"/>
<div class="d-flex">
    <jsp:include page="include/client_menu.jsp"/>

    <div id="main-block" class="content p-4">
        <div class="container col-4">
            <table id="stations" class="table table-striped table-bordered table-light">
                <thead>
                <tr>
                    <th>Station</th>
                    <th>Watch timetable</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${stations}" var="station">
                    <tr>
                        <td>
                            ${station.name}
                        </td>
                        <td>
                            <a class="btn btn-primary" href="/client/departures/${station.name}" target="_blank">
                                Watch timetable
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="include/client_js.jsp"/>
<script src="/resource/js/client-get-all-stations.js"></script>

</body>
</html>
