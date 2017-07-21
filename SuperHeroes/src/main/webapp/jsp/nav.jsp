<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="navbar">
    <ul class="nav nav-tabs">
        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/metahumans">MetaHumans</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/organizations">Organizations</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/locations">Locations</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/sightings">Sightings</a></li>

    </ul>
</div>

<!--    some kind of if else statement for active tab needed-->
