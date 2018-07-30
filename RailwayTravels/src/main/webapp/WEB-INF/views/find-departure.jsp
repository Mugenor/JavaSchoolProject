<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <base href="${pageContext.request.contextPath}/">

    <jsp:include page="include/client_css.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resource/vendor/jquery-ui/jquery-ui.css"/>"/>
    <link rel="stylesheet"
          href="<c:url value="/resource/vendor/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>"/>

    <title>Client page</title>
</head>
<body class="bg-light">
<jsp:include page="include/header.jsp"/>
<div class="d-flex">
    <jsp:include page="include/client_menu.jsp"/>

    <div id="main-block" class="content p-4">
        <div class="card mb-4">
            <div class="card-header">
                <form class="form-inline" id="departure-form" autocomplete="off">
                    <div>
                        <input type="text" class="autocomplete form-control mr-sm-2" id="stationFrom"
                               placeholder="Station from" required/>
                        <div id="validStationFrom">
                        </div>
                    </div>
                    <div>
                        <input type="text" class="autocomplete form-control mr-sm-2" id="stationTo"
                               placeholder="Station to"
                               required/>
                        <div id="validStationTo">
                        </div>
                    </div>
                    <div>
                        <input type="text" class="form-control mr-sm-2" id="dateTimeFrom" placeholder="Departure time"
                               required/>
                        <div id="validDateTimeFrom">
                        </div>
                    </div>
                    <div>
                        <input type="text" class="form-control mr-sm-2" id="dateTimeTo" placeholder="Arrival time"
                               required/>
                        <div id="validDateTimeTo">
                        </div>
                    </div>
                    <button id="search" type="submit" class="btn btn-primary">Search</button>
                </form>
            </div>
            <div class="card-body">
                <div id="departure-container">
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
    </div>
</div>

<jsp:include page="include/client_js.jsp"/>
<script src="<c:url value="/resource/vendor/jquery-ui/jquery-ui.js"/>"></script>
<script src="<c:url value="/resource/vendor/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"/>"></script>
<script src="<c:url value="/resource/js/client-find-departure.js"/>"></script>

</body>
</html>