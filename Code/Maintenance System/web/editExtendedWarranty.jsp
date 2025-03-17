<%-- 
    Document   : editExtendedWarranty
    Created on : Mar 16, 2025, 8:57:34 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Extended Warranty</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Edit Extended Warranty</h1>
    <form action="ExtendedWarrantyController" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="extendedWarrantyID" value="${extendedWarranty.extendedWarrantyID}">
        <div class="mb-3">
            <label for="extendedWarrantyName" class="form-label">Extended Warranty Name</label>
            <input type="text" class="form-control" id="extendedWarrantyName" name="extendedWarrantyName" value="${extendedWarranty.extendedWarrantyName}" required>
        </div>
        <div class="mb-3">
            <label for="extendedPeriodInMonths" class="form-label">Extended Period (Months)</label>
            <input type="number" class="form-control" id="extendedPeriodInMonths" name="extendedPeriodInMonths" value="${extendedWarranty.extendedPeriodInMonths}" required>
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">Price</label>
            <input type="text" class="form-control" id="price" name="price" value="${extendedWarranty.price}" required>
        </div>
        <div class="mb-3">
            <label for="extendedWarrantyDescription" class="form-label">Description</label>
            <textarea class="form-control" id="extendedWarrantyDescription" name="extendedWarrantyDescription" rows="3">${extendedWarranty.extendedWarrantyDescription}</textarea>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
        <a href="ExtendedWarrantyController" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>

