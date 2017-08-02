<%--
    Document   : blogPageDisplayActiveByCategory
    Created on : Jul 27, 2017, 6:13:37 PM
    Author     : admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<!--CHANDLER/MIKE home controller needs hooked up so that it will display what is below.
Database is populated, so we should be able to test it. -->

<html>
    <head>
        <title>Blog Posts</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
        <style>
            /*Has to be internal, bootstrap is overriding due to specificity*/
            h1 {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Active Blogs By Category</h1>
            <hr/>
            <div class="navbar">
                <jsp:include page="nav.jsp" />
            </div>
        </div>
        <div class="container">
            <div class="form-group">
                <label for="add-category" class="col-md-2 control-label"> Category:</label>
                <div class="col-md-10">
                    <select class="col-md-5" name="category" placeholder="Select Category" required onchange="location = this.value;">
                        <option value="Choose Category" disabled="" selected="selected">Choose Category</option> 
                        <c:forEach var="categoryList" items="${categoryList}">
                            <option value="${pageContext.request.contextPath}/chooseCategory/${categoryList.categoryID}">
                                ${categoryList.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="container">
            <!-- Main Page Content Start -->
            <div class="row">
                <div class="col-md-12">
                    <br>
                    <table id="blogPostTable" class="table table-hover">
                        <tr>

                            <th width="40%">Blog Title</th>
                            <th width="20%">Author</th>
                            <th width="20%">Post Date</th>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <th width="10%">Edit</th>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <th width="10%">Delete</th>
                        </sec:authorize>
                        </tr>
                        <c:forEach var="bridge" items="${bridgeList}">
                            <tr>

                                <td>
                                    <a href="${pageContext.request.contextPath}/blogPageDisplayByID?blogPostID=${bridge.blogPost.blogPostID}">
                                        <c:out value="${bridge.blogPost.title}"/>
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${bridge.blogPost.user.userName}"/>
                                </td>
                                <td>
                                    <c:out value="${bridge.blogPost.startDate}"/>
                                </td>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td>
                                    <a href="displayAdminUpdateBlogPostPage?blogPostID=${bridge.blogPost.blogPostID}">
                                        Edit
                                    </a>

                                </td>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <td>
                                    <a href="adminDeleteBlogPost?blogPostID=${bridge.blogPost.blogPostID}">
                                        Set Inactive
                                    </a>
                                </td>
                            </sec:authorize>
                            </tr>
                        </c:forEach>
                    </table>
                </div> <!-- End col div -->
            </div>
        </div>
        <footer>
            <jsp:include page="footer.jsp"/>
        </footer>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
