<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer Detail</title>
</head>
<body>
    <h2>Customer Detail</h2>

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
    <a href="customer">Back to List</a>
</body>
</html>
