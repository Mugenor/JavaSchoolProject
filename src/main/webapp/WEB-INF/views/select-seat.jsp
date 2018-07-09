<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="seatsInCoach" value="35"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <jsp:include page="include/client_css.jsp"/>
    <style>
        .coach {
            display: none;
        }

        .active {
            display: block;
        }

        .layer {
            position: absolute;
        }
    </style>

    <title>Client page</title>
</head>
<body class="bg-light">
<jsp:include page="include/client_header.jsp"/>
<div class="d-flex">
    <jsp:include page="include/client_menu.jsp"/>

    <div id="main-block" class="content p-4">
        <c:forEach var="couachNum" begin="1" end="${departure.sitsCount / seatsInCoach}">
            <div class="pic" style="position: relative">
                <div class="layer coach active" style='z-index: 100;left: 0px; top: 0px;width: 761px; height: 127px; line-height: 127px;
                background-image: url("https://cdn1.tu-tu.ru/images2/train/order/car/re/coupe_normal.png")'></div>
                <div class="layer coach" style='z-index: 100;left: 0px; top: 0px;width: 761px; height: 127px; line-height: 127px;
                background-image: url("https://cdn1.tu-tu.ru/images2/train/order/car/re/coupe_hover.png")'></div>
                <div class="layer"
                     style="z-index: 101;left: 36px; top: 0px; width: 22px; height: 17px; line-height: 17px;">
                    <span class="content">${couachNum}</span>
                </div>
                <c:forEach var="ticket" items="">

                </c:forEach>
            </div>
        </c:forEach>

    </div>
</div>

<jsp:include page="include/client_js.jsp"/>
<script src="<c:url value="/resource/js/client-select-seat.js"/>"></script>
</body>
</html>
