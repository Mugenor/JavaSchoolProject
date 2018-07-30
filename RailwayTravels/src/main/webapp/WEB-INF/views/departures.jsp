<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <tbody>
                <c:forEach items="${departures}" var="departure">
                    <tr>
                        <td>
                                ${departure.stationFrom}
                        </td>
                        <td>
                                ${departure.stationTo}
                        </td>
                        <td data-order="${departure.dateTimeFrom}">
                            <fmt:formatDate value="${departure.dateTimeFromAsDate}" type="both"
                                            pattern="dd.MM.yyyy, HH:mm"/>
                        </td>
                        <td data-order="${departure.dateTimeTo}">
                            <fmt:formatDate value="${departure.dateTimeToAsDate}" type="both"
                                            pattern="dd.MM.yyyy, HH:mm"/>
                        </td>
                        <td>
                                ${departure.freeSitsCount}
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${departure.tooLate}">
                                    Too late!
                                </c:when>
                                <c:when test="${departure.freeSitsCount <= 0}">
                                    All seats are engaged!
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-primary" href="client/select/${departure.id}" target="_blank">
                                        Buy ticket!
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="include/client_js.jsp"/>
<script src="resource/js/client-get-all-departures.js"></script>

</body>
</html>
