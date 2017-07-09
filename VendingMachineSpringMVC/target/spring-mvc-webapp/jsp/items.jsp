<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine Web Service</title>
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.css"
              rel="stylesheet">
        <link href="css/myCss.css"
              rel="stylesheet">

        <style>
            .itemBlock {
                margin: 40px;
            }
        </style>

    </head>
    <body>

        <div id="header">
            <center>
                <h1>Vending Machine</h1>
            </center>
        </div>

        <hr>

        <ul class="list-group" id="errorMessages">
        </ul>

        <div class="container col-sm-9" id="left-side-container">
            <div  id="product-list">


                <!--                    #3: This holds the list of contacts - we will add rows
                                    dynamically
                                    using jQuery-->

                <div id="contentRows">
                    <c:forEach var="currentItem" items="${itemList}">
                        <div class="col-sm-4">
                            <a href="displayItemDetails?itemId=${currentItem.id}">
                                <div class="btn btn-default btn-block itemBlock"
                                     id="item-button">
                                    <div align="left"> <c:out value="${currentItem.id}"/>
                                    </div> <c:out value="${currentItem.name}"/><br><br>
                                    $<c:out value="${currentItem.price}"/> <br><br><br>
                                    Quantity Left:  <c:out value="${currentItem.stock}"/>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>

            </div>
        </div>

        <div class="container col-sm-3" id="right-side-container">

            <div class="row" id="row1">
                <div class id="money-display">
                    <center><h2>Total $ In</h2></center>
                    <br>
                    <div>
                        <center>
                            <form class="form-inline" role="form">

                                <div class="form-group">

                                    <input class="form-control "
                                           id="show-money"
                                           value="<c:out value="${money.amount}"/>"
                                           type="text"
                                           readonly/>

                                </div>

                            </form>
                        </center>
                    </div>
                    <br>


                    <div id="dollar-quarter-buttons" align="center">
                        <a href="addDollar">
                            <button class="btn btn-default" type="button" id="dollar-button">
                                Add Dollar
                            </button>
                        </a>
                        <a href="addQuarter">
                            <button class="btn btn-default" type="button" id="quarter-button">
                                Add Quarter
                            </button>
                        </a>
                    </div>
                    <br>
                    <div id="dime-nickel-buttons" align="center">
                        <a href="addDime">
                            <button class="btn btn-default" type="button" id="dime-button">
                                Add Dime
                            </button>
                        </a>
                        <a href="addNickel">
                            <button class="btn btn-default" type="button" id="nickel-button">
                                Add Nickel
                            </button>
                        </a>
                    </div>
                </div>


                <hr>
            </div>

            <div class="row" id="row2">
                <div id="purchase-display">
                    <center><h2>Messages</h2></center>
                    <br>
                    <center>
                        <form class="form-inline" role="form">
                            <div class="form-group">
                                <input class="form-control"
                                       id="show-message"
                                       value="<c:out value="${message}"/>"
                                       readonly/>
                            </div>
                        </form>
                    </center>
                    <br>
                    <br>
                    <center>
                        <form class="form-inline" role="form" modelAttribute="item"
                              action="purchaseItem" method="POST">
                            <label for="item-select">Item:</label>
                            <div class="form-group">

                                <input class="form-control"
                                       id="item-select"
                                       type="text"
                                       value="<c:out value="${item.id}"/>"
                                       readonly/>

                            </div>
                            <br>
                            <div align="center">
                                <button class="btn btn-default" type="submit" id="purchase-button" value="Purchase Item">
                                    Make Purchase
                                </button>
                            </div>
                        </form>
                    </center>
                </div>


                <hr>
            </div>

            <div class="row" id="row3">
                <div id="change-display">
                    <center><h2>Change</h2></center>
                    <br>
                    <center>
                        <form class="form-inline" role="form">
                            <div class="form-group">
                                <input class="form-control"
                                       id="show-change"
                                       value="<c:out value="${change}"/>"
                                       readonly/>
                            </div>
                        </form>
                    </center>
                    <br>
                    <div align="center">
                        <a href="clearChange">
                            <button class="btn btn-default" type="button" id="change-button">
                                Change Return
                            </button>
                        </a>
                    </div>
                </div>
            </div>



        </div>

        <!-- #5: Placed at the end of the document so the pages load faster -->
        <script src="js/jquery-2.2.4.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/home.js"></script>
    </body>
</html>

