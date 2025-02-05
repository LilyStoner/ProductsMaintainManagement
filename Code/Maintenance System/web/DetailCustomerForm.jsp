<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>Customer Detail</title>

    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>

<body>
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />

        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">

                <h2>Customer Detail</h2>

                <!-- Customer Details Table -->
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <td>${customer.customerID}</td>
                    </tr>
                    <tr>
                        <th>Name</th>
                        <td>${customer.name}</td>
                    </tr>
                    <tr>
                        <th>Image</th>
                        <td><img src="${customer.image}" alt="Customer Image" width="200" height="200" /></td>
                    </tr>
                    <tr>
                        <th>Address</th>
                        <td>${customer.address}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${customer.email}</td>
                    </tr>
                    <tr>
                        <th>Phone</th>
                        <td>${customer.phone}</td>
                    </tr>
                </table>

                <br>
                <a href="update">Update </a>
                <a href="customer">Back to List</a>

            </main>
            <jsp:include page="/includes/footer.jsp" />
        </div>
    </div>

    <script src="js/app.js"></script>
</body>

</html>
