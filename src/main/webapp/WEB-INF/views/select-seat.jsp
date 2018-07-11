<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <jsp:include page="include/client_css.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resource/css/select-seat.css"/>"/>

    <title>Client page</title>
</head>
<body class="bg-light">
<jsp:include page="include/header.jsp"/>
<div class="d-flex">
    <jsp:include page="include/client_menu.jsp"/>

    <div id="main-block" class="aling-content-center content p-4">
        <div class="card mb-4">
            <div class="card-header bg-light">
                <div class="row">From ${departure.stationTo.title} to ${departure.stationFrom.title}</div>
                <div class="row">Start: ${departure.dateTimeFrom.toString('dd.MM.yyyy, HH:mm')}</div>
                <div class="row">Arrival: ${departure.dateTimeTo.toString('dd.MM.yyyy, HH:mm')}</div>
            </div>
            <div class="card-body">
                <div data-iddeparture="${departure.id}">
                    <c:forEach items="${departure.coaches}" var="coach">
                        <div class="pic">
                            <div class="layer coach active" style='z-index: 100;left: 0px; top: 0px;width: 761px; height: 127px; line-height: 127px;
                background-image: url("https://cdn1.tu-tu.ru/images2/train/order/car/re/coupe_normal.png")'></div>
                            <div class="layer coach" style='z-index: 100;left: 0px; top: 0px;width: 761px; height: 127px; line-height: 127px;
                background-image: url("https://cdn1.tu-tu.ru/images2/train/order/car/re/coupe_hover.png")'></div>
                            <div class="layer"
                                 style="z-index: 101;left: 36px; top: 0px; width: 22px; height: 17px; line-height: 17px; text-align: center">
                                <span class="content">${coach.coachNumber}</span>
                            </div>
                            <c:forEach var="ticket" items="${coach.tickets}">
                                <div data-seatnum="${ticket.siteNum}" data-coachnum="${coach.coachNumber}"
                                     class="layer seat<c:choose><c:when test="${ticket.passenger != null}"> engaged</c:when>
                                            <c:otherwise> free</c:otherwise></c:choose>"
                                     <c:if test="${ticket.passenger != null}">title="Seat already engaged!"</c:if>
                                     <c:if test="${ticket.passenger == null}">title="Buy seat!"</c:if>
                                     style="
                                     <c:choose>
                                     <c:when test="${ticket.siteNum % 4 == 3}">
                                             left: ${157 + 65 * ((ticket.siteNum - (ticket.siteNum % 4)) / 4)}px;
                                             top: ${19 + (ticket.siteNum % 2) * 25}px;
                                     </c:when>
                                     <c:when test="${ticket.siteNum % 4 == 0}">
                                             left: ${157 + 65 * (((ticket.siteNum - (ticket.siteNum % 4)) / 4) - 1)}px;
                                             top: ${19 + (ticket.siteNum % 2) * 25}px;
                                     </c:when>
                                     <c:otherwise>
                                             left: ${117 + 65 * ((ticket.siteNum - (ticket.siteNum % 4)) / 4)}px;
                                             top: ${19 + (ticket.siteNum % 2) * 25}px;
                                     </c:otherwise>
                                             </c:choose>">
                                    <span class="">${ticket.siteNum}</span>
                                </div>

                            </c:forEach>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="card-footer">
                <div class="row">
                    <button id="buyTicketButton" class="btn btn-success btn-lg">Buy ticket!</button>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="include/client_js.jsp"/>
<script src="<c:url value="/resource/js/client-select-seat.js"/>"></script>
</body>
</html>
