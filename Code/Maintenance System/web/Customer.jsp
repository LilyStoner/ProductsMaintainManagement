<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer List</title>
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
            }
            th {
                background-color: #f2f2f2;
                text-align: left;
            }
            tr:hover {
                background-color: #f5f5f5;
            }
            h2, h3 {
                text-align: center;
            }
        </style>
    </head>
    <body>
        <h2>Customer Management</h2>


        <form action="customer" method="get" style="text-align: center; margin-bottom: 20px;">
            <input type="hidden" name="action" value="search" />
            <input type="text" name="text" placeholder="Search by name" value="${textSearch}" />
            <button type="submit">Search</button>
        </form>


        <c:choose>
            <c:when test="${not empty listSearchCustomer}">
                <h3>Search Results for "${textSearch}"</h3>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Image</th>
                        <th>Address</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                    <c:forEach var="customer" items="${listSearchCustomer}">
                        <tr>
                            <td>${customer.customerID}</td>
                            <td>${customer.name}</td>
                            <td><img src="${customer.image}" alt="Customer Image" width="100" height="100" /></td>
                            <td>${customer.address}</td>
                            <td>${customer.email}</td>
                            <td>${customer.phone}</td>
                            <td>
                                <a href="customer?action=detail&id=${customer.customerID}">Detail</a>
                                <a href="customer?action=update&id=${customer.customerID}">Update</a>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
            </c:when>


            <c:otherwise>
                <h3>All Customers</h3>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Image</th>
                        <th>Address</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                    <c:forEach var="customer" items="${listCustomer}">
                        <tr>
                            <td>${customer.customerID}</td>
                            <td>${customer.name}</td>
                            <td><img src="${customer.image}" alt="Customer Image" width="100" height="100" /></td>
                            <td>${customer.address}</td>
                            <td>${customer.email}</td>
                            <td>${customer.phone}</td>
                            <td>
                                <a href="customer?action=detail&id=${customer.customerID}">Detail</a>
                                <a href="customer?action=update&id=${customer.customerID}">Update</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </body>
</html>
