<%--
    Document   : staticPageEdit
    Created on : Jul 22, 2017, 10:17:54 AM
    Author     : chandler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Static Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
        <script src='${pageContext.request.contextPath}/js/tinymce/js/tinymce/tinymce.min.js'></script>
        <script src="${pageContext.request.contextPath}/js/tinyMCEinit.js" type="text/javascript"></script>

        <style>
            h1 {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <h1>Edit Static Page</h1>
        <jsp:include page="nav.jsp"/>

        <sf:form class="form-horizontal" role="form" id="edit-staticPage-form" modelAttribute="staticPage"
                 action="editStaticPage" method="POST">

            <label for ="edit-staticPage-title" class="col-sm-2"><h3>Static Page Title:</h3></label>
            <sf:input class="form-control"
                      id="edit-staticPage-title"
                      type="text"
                      placeholder="Static Page Title"
                      path="staticPageName"
                      />



            <sf:textarea id="tinyMCE" path="staticPageContent"></sf:textarea>
                <input name="image" type="file" id="upload" class="hidden" onchange="">

                <!--TINY MCE GOES HERE-->

            <sf:input class="form-control"
                      id="decide-nav-index"
                      type="text"
                      placeholder="Place in nav index"
                      path="navIndex"/>
            <!--                   LOL NOT TEXT-->

            <sf:input type="hidden" path="staticPageID"/>



            <button class="btn btn-default"
                    id="save-staticPage-button"
                    type="submit">
                Edit Page
            </button>
        </sf:form>

        <form action="${pageContext.request.contextPath}/displayAdminPage" method="GET">
            <button class="btn btn-default"
                    id="cancel-staticPage-creation-button"
                    type="submit"
                    >
                Cancel
            </button>
        </form>





        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
