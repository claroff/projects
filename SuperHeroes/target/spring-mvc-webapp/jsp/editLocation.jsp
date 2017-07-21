<%--
    Document   : editMetaHuman
    Created on : Jul 12, 2017, 11:09:14 AM
    Author     : chandler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Location</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Edit Location</h1>
            <hr/>
            <jsp:include page="nav.jsp"/>


            <sf:form class="form-horizontal" role="form" id="edit-loc-form" modelAttribute="location"
                     action="editLocation" method="POST">
                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Location name:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-loc-name-input"
                                  type="text"
                                  placeholder="Location Name"
                                  path ="name"
                                  />
                    </div>
                </div>
                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Description:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-identity-input"
                                  type="text"
                                  placeholder="Location Description"
                                  path="description"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Address:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-identity-input"
                                  type="text"
                                  placeholder="Address"
                                  path="address"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Latitude:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-identity-input"
                                  type="text"
                                  placeholder="Latitude"
                                  path="latitude"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for ="edit-MHname-input" class="col-sm-2"><h3>Longitude:</h3></label>
                    <div class="col-sm-2">
                        <h3></h3>
                        <sf:input class="form-control"
                                  id="edit-identity-input"
                                  type="text"
                                  placeholder="Longitude"
                                  path="longitude"/>
                    </div>
                </div>


                <div class="col-md-offset-4 col-md-4">
                    <sf:input type="hidden" path="locationID"/>
                </div>

                <div class="col-sm-1">
                    <button class="btn btn-default"
                            id="save-edit-button"
                            type="submit">
                        Save Changes
                    </button>
                </div>
            </sf:form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>