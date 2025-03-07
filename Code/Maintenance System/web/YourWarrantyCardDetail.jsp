<%-- 
    Document   : ComponentWarehouse
    Created on : Jan 17, 2025, 8:24:34 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
        <meta name="author" content="AdminKit">
        <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/" />

        <title>Component Warehouse</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                         <c:if test="${not empty requestScope.warrantyCard}">
                        <table class="table table-bordered">
                            <tr><th>Warranty Card ID:</th><td>${warrantyCard.warrantyCardID}</td></tr>
                            <tr><th>Warranty Card Code:</th><td>${warrantyCard.warrantyCardCode}</td></tr>
                            <tr><th>Product Detail Code:</th><td>${warrantyCard.productDetailCode}</td></tr>
                            <tr><th>Product Code:</th><td>${warrantyCard.productCode}</td></tr>
                            <tr><th>Product Name:</th><td>${warrantyCard.productName}</td></tr>
                            <tr><th>Issue Description:</th><td>${warrantyCard.issueDescription}</td></tr>
                            <tr><th>Warranty Status:</th><td>${warrantyCard.warrantyStatus}</td></tr>
                            <tr><th>Created Date:</th><td>${warrantyCard.createdDate}</td></tr>
                            <tr><th>Return Date:</th><td>${warrantyCard.returnDate}</td></tr>
                            <tr><th>Done Date:</th><td>${warrantyCard.donedDate}</td></tr>
                            <tr><th>Completed Date:</th><td>${warrantyCard.completedDate}</td></tr>
                            <tr><th>Cancelled Date:</th><td>${warrantyCard.canceldDate}</td></tr>
                            <tr>
                                <th>Image:</th>
                                <td>
                                    <c:if test="${not empty warrantyCard.image}">
                                        <img src="${warrantyCard.image}" alt="Warranty Image" width="200">
                                    </c:if>
                                    <c:if test="${empty warrantyCard.image}">
                                        No image available.
                                    </c:if>
                                </td>
                            </tr>
                            
                            <tr><th>Customer Name:</th><td>${warrantyCard.customerName}</td></tr>
                            <tr><th>Customer Phone:</th><td>${warrantyCard.customerPhone}</td></tr>
                            
                           
                           

                        </table>
                    </c:if>

 
                    <button>Export PDF</button>
                    <button><a href="yourwarrantycard">Back to list</a></button>
                    
                    
                    
                    
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>

    </body>

</html>
