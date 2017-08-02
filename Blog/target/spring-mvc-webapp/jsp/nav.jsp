<%--
    Document   : nav
    Created on : Jul 19, 2017, 5:09:51 PM
    Author     : chandler
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="row">
    <div class="col-md-10">
    </div>
    <div>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            <form class="form-inline" action="${pageContext.request.contextPath}/login">
                <input id="signInButton" class="btn btn-primary navbar-btn" type ="submit" value="Sign In">
            </form>
            <form class="form-inline" action="${pageContext.request.contextPath}/createAccountForm">
                <input id="signUpButton" class="btn btn-primary navbar-btn" type ="submit" value="Create Account">
            </form> 
        </c:if>
    </div>
</div>
<div class="navbar">
    <ul class="nav nav-tabs">
        <li role="presentation"><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li role="presentation" ><a href="${pageContext.request.contextPath}/displayAllPostsByCategory">Post Archives</a></li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <li role="presentation"><a href="${pageContext.request.contextPath}/displayAdminPage">Admin Page</a></li>
            </sec:authorize>
            <c:forEach var="staticPage" items="${staticPageList}">
            <li role="presentation">
                <a href="${pageContext.request.contextPath}/displayStaticPage?staticPageID=${staticPage.staticPageID}">
                    ${staticPage.staticPageName}
                </a>
            </li>
        </c:forEach>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
            <p class="btn btn-default" style="background-color: red; color: white">Hello : ${pageContext.request.userPrincipal.name}!
                <a class="btn btn-default btn-primary" href="<c:url value="/j_spring_security_logout" />" > Logout</a>
            </p>
        </c:if> 
    </ul>
</div>