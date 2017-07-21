<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Organizations</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <center>
                <h1>Organization Database</h1>
            </center>
            <hr/>
            <jsp:include page="nav.jsp"/>

            <form class="form-horizontal" role="form" id="get-search-term-form"
                  action="displayCreateOrganizationForm" method="GET">
                <div class="col-sm-3" >
                    <button class="btn btn-default"
                            id="search-button"
                            type="submit">
                        Add New Organization
                    </button>
                </div>
            </form>

            <form class="form-horizontal" role="form" id="get-search-term-form"
                  action="searchOrganizations" method="GET">

                <!--                <div class="col-sm-3">
                                    <select class="form-control" required id="category-dropdown">
                                        <option value="" hidden id="default">Search Category</option>
                                        <option value="title" id="title">Title</option>
                                        <option value="year" id="release-date">Release Date</option>
                                        <option value="director" id="director">Director</option>
                                        <option value="rating" id="rating">Rating</option>
                                    </select>
                                </div>-->

                <div class="form-group">
                    <div class="col-sm-2">
                        <input class="form-control"
                               id="search-input"
                               type="text"
                               placeholder="Search Term"
                               required/>
                    </div>
                    <div class="col-sm-3" >
                        <button class="btn btn-default"
                                id="search-button"
                                type="submit">
                            Search
                        </button>
                    </div>
                </div>

            </form>

            <!--            <h2>MetaHuman List</h2>-->

            <div class="container" id="organization-list">
                <table id="contactTable" class="table table-hover">
                    <tr>
                        <th width="75%">Name</th>
                        <th width="25%"></th>
                    </tr>

                    <tbody id="contentRows">
                        <c:forEach var="currentOrganization" items="${organizationList}">
                            <tr>
                                <td> <span value="displayOrganizationInfo" method = "GET"><a href="displayOrganizationInfo?organizationId=${currentOrganization.organizationID}">
                                            ${currentOrganization.name}</a></span></td>
                                <td> <span value="displayEditOrganizationForm" method="GET"><a href="displayEditOrganizationForm?organizationId=${currentOrganization.organizationID}">
                                            Edit</a></span> | <span value="deleteOrganization" method="GET"><a href="deleteOrganization?organizationId=${currentOrganization.organizationID}"  onclick="return confirm('Are you sure you want to delete?')">
                                            Delete
                                        </a></span>
                                </td>

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

