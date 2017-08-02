<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Blog Login</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-9"></div>
                <div class="col-md-3">
                    <a style="display: inline-block" class="btn btn-default btn-primary"  href="${pageContext.request.contextPath}/createAccountForm">Create Account</a>
                    <a style="display: inline-block" class="btn btn-default btn-primary"  href="${pageContext.request.contextPath}/">Home</a>
                </div>
            </div>
            <h2>Login Page</h2>

            <c:if test="${param.login_error == 1}">
                <h3>Wrong id or password!</h3>
            </c:if>
            <form class="form-horizontal"
                  role="form"
                  method="post"
                  action="j_spring_security_check">
                <div class="form-group">
                    <label for="j_username"
                           class="col-md-4 control-label">Username:</label>
                    <div class="col-md-8">
                        <input type="text"
                               class="form-control"
                               name="j_username"
                               placeholder="Username"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="j_password"
                           class="col-md-4 control-label">Password:</label>
                    <div class="col-md-8">
                        <input type="password"
                               class="form-control"
                               name="j_password"
                               placeholder="Password"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit"
                               class="btn btn-default btn-primary"
                               id="search-button"
                               value="Sign In"/>
                    </div>
                </div>
            </form>

            <br>

        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>