<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>H.E.R.O. Database</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <center>
                <h1>H.E.R.O. MetaHuman Tracker</h1>
            </center>
            <hr/>
            <jsp:include page="nav.jsp"/>
            <h2>Latest Sightings</h2>

            <div class="container" id="organization-list">
                <table id="contactTable" class="table table-hover">
                    <tr>
                        <th width="35%">Name</th>
                        <th width="30%">MetaHumans Involved</th>
                        <th width="15%">Location</th>
                        <th width="10%">Date</th>

                    </tr>

                    <tbody id="contentRows">

                        <c:forEach var="map" items="${map}">
                            <tr>
                                <td> <span value="displaySightingInfo" method = "GET"><a href="displaySightingInfo?sightingId=${map.key.sightingID}">
                                            ${map.key.name}</a></span></td>
                                <td>|
                                    <c:forEach var="metaHuman" items="${map.value}">
                                        <span value="displayMetaHumanInfo" method = "GET"><a href="displayMetaHumanInfo?metaHumanId=${metaHuman.metaHumanID}">
                                                ${metaHuman.name}</a> | </span>
                                            </c:forEach>
                                </td>
                                <td><span value="displayLocationInfo" method = "GET"><a href="displayLocationInfo?locationId=${map.key.location.locationID}">
                                            ${map.key.location.name}</a></span></td>
                                <td>${map.key.date}</td>

                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

