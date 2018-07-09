<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar sidebar-dark bg-dark">
    <ul class="list-unstyled">
        <li>
            <a href="#sm_expand_1" data-toggle="collapse">
                <i class="fa fa-fw fa-link"></i> Departures
            </a>
            <ul id="sm_expand_1" class="list-unstyled collapse">
                <li id="all-departures"><a href="<c:url value="/all-departures"/>">All departures</a></li>
                <li><a href="#">Find departure</a></li>
            </ul>
        </li>
        <li id="all-stations"><a href="#all-stations"><i class="fa fa-fw fa-link"></i> Stations</a></li>
    </ul>
</div>
